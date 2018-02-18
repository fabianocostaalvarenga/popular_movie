package com.udacity.filmesfamosos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.filmesfamosos.Adapter.ImageAdapter;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.MovieDTO;
import com.udacity.filmesfamosos.tasks.AsyncTaskDelegate;
import com.udacity.filmesfamosos.tasks.ThumbnailAsyncTaskExecutor;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements AsyncTaskDelegate<List<MovieDTO>> {

    private GridView gridView;
    private ProgressDialog progressDialog = null;
    private static FilterEnum latestFilter = FilterEnum.POPULAR_MOVIES;
    private List<MovieDTO> movieDTOs;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        checkTextTitlePage();

        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openDetailView((MovieDTO) adapterView.getItemAtPosition(position));
            }
        });

        if(null == savedInstanceState)
            executeRequest(latestFilter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("dtos", (ArrayList<? extends Parcelable>) this.movieDTOs);
        outState.putParcelable("imgAdapter", imageAdapter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.movieDTOs = savedInstanceState.getParcelableArrayList("dtos");
        this.imageAdapter = savedInstanceState.getParcelable("imgAdapter");
        gridView.setAdapter(this.imageAdapter);
    }

    private void checkTextTitlePage() {
        if (FilterEnum.POPULAR_MOVIES.equals(latestFilter)) {
            this.setTitle(R.string.item_menu_popular_movie);
        } else if (FilterEnum.TOP_RATED.equals(latestFilter)) {
            this.setTitle(R.string.item_menu_top_rated);
        } else if (FilterEnum.FAVORITE.equals(latestFilter)) {
            this.setTitle(R.string.item_menu_favorites);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.popular_movie) {
            executeRequest(FilterEnum.POPULAR_MOVIES);
        } else if(item.getItemId() == R.id.top_rated) {
            executeRequest(FilterEnum.TOP_RATED);
        } else if(item.getItemId() == R.id.favorite) {
            executeRequest(FilterEnum.FAVORITE);
        }

        checkTextTitlePage();

        return true;
    }

    private void openDetailView(MovieDTO movieDTO) {
        Intent intent = new Intent(PrincipalActivity.this, DetailMovieActivity.class);
        intent.putExtra(MovieDTO.POPULAR_MOVIE_DTO, movieDTO);
        intent.putExtra("LATEST_FILTER", latestFilter);
        startActivity(intent);
    }

    private void executeRequest(final FilterEnum filterEnum) {

        latestFilter = filterEnum;

        if(FilterEnum.FAVORITE.equals(filterEnum)) {
            new ThumbnailAsyncTaskExecutor(this).execute(filterEnum);
            return;
        }

        if(NetWorkUtils.isOnline(this)) {
            new ThumbnailAsyncTaskExecutor(this).execute(filterEnum);
        } else {
            View view = findViewById(R.id.activity_principal);
            Snackbar snackbar =
                    Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.label_retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeRequest(filterEnum);
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void processStart() {

        if(progressDialog != null) {
            progressDialog.cancel();
        }

        progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait), true, true);
    }

    @Override
    public void processFinish(List<MovieDTO> movieDTOs) {

        this.movieDTOs = movieDTOs;
        this.imageAdapter = new ImageAdapter(this, movieDTOs);

        gridView.setAdapter(this.imageAdapter);

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(FilterEnum.FAVORITE.equals(latestFilter)) {
            new ThumbnailAsyncTaskExecutor(this).execute(latestFilter);
        }
    }
}
