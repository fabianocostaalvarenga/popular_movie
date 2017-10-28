package com.udacity.filmesfamosos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.tasks.ListThumbnailAsyncTaskExecutor;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

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
        if(NetWorkUtils.isOnline(this)) {
            new ListThumbnailAsyncTaskExecutor(PrincipalActivity.this, gridView, filterEnum).execute();
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
}
