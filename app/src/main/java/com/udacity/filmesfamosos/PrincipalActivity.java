package com.udacity.filmesfamosos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.filmesfamosos.Adapter.ImageAdapter;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.tasks.AsyncTaskDelegate;
import com.udacity.filmesfamosos.tasks.ListThumbnailAsyncTaskExecutor;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements AsyncTaskDelegate<List<PopularMovieDTO>> {

    private GridView gridView;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                openDetailView((PopularMovieDTO) adapterView.getItemAtPosition(position));
            }
        });

        executeRequest(FilterEnum.POPULAR_MOVIES);

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
            this.setTitle(R.string.item_menu_popular_movie);
        } else if(item.getItemId() == R.id.top_rated) {
            executeRequest(FilterEnum.TOP_RATED);
            this.setTitle(R.string.item_menu_top_rated);
        }

        return true;
    }

    private void openDetailView(PopularMovieDTO popularMovieDTO) {
        Intent intent = new Intent(PrincipalActivity.this, DetailMovieActivity.class);
        intent.putExtra(PopularMovieDTO.POPULAR_MOVIE_DTO, popularMovieDTO);
        startActivity(intent);
    }

    private void executeRequest(final FilterEnum filterEnum) {
        if(NetWorkUtils.isOnline(this)) {
            new ListThumbnailAsyncTaskExecutor(this).execute(filterEnum);
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
    public void processFinish(List<PopularMovieDTO> object) {

        gridView.setAdapter(new ImageAdapter(this, object));

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

}
