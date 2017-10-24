package com.udacity.filmesfamosos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.tasks.ListThumbnailAsyncTaskExecutor;

public class PrincipalActivity extends AppCompatActivity {

    private static final String TAG = "PrincipalActivity";

    private GridView gridView;

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
        intent.putExtra("popularMovieDTO", popularMovieDTO);
        startActivity(intent);
    }

    private void executeRequest(final FilterEnum filterEnum) {
        new ListThumbnailAsyncTaskExecutor(PrincipalActivity.this, gridView, filterEnum,
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).execute();
    }
}
