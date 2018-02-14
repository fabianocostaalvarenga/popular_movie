package com.udacity.filmesfamosos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.udacity.filmesfamosos.Adapter.TrailerLineRecycleAdapter;
import com.udacity.filmesfamosos.databinding.ActivityDetailBinding;
import com.udacity.filmesfamosos.model.TrailerModel;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.tasks.AsyncTaskDelegate;
import com.udacity.filmesfamosos.tasks.ListTrailersAsyncTaskExecutor;
import com.udacity.filmesfamosos.utils.DateUtils;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class DetailMovieActivity extends AppCompatActivity implements AsyncTaskDelegate<List<TrailerModel>> {

    private PopularMovieDTO popularMovieDTO;
    private ProgressDialog progressDialog = null;
    private Button btFavorite;

    private ActivityDetailBinding activityDetailBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.setTitle(R.string.activity_title_detail);

        Intent intent = this.getIntent();

        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        if(null != intent.getExtras()) {
            popularMovieDTO = (PopularMovieDTO) intent.getExtras().get(PopularMovieDTO.POPULAR_MOVIE_DTO);
            setComponentsValues(popularMovieDTO);
        }

        executeRequest(popularMovieDTO);
    }

    private void mountListTrailersItens(List<TrailerModel> trailerModels) {
        RecyclerView trailersRecycle = activityDetailBinding.lvTrailers;
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trailersRecycle.setLayoutManager(layout);

        trailersRecycle.setAdapter(new TrailerLineRecycleAdapter(this, trailerModels, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ((AppCompatTextView) v).getText(), Toast.LENGTH_LONG).show();
            }
        }));

    }

    private void setComponentsValues(PopularMovieDTO popularMovieDTO) {

        btFavorite = activityDetailBinding.btFavorite;

        activityDetailBinding.tvOriginalTitle.setText(popularMovieDTO.getOriginalTitle());
        activityDetailBinding.tvReleaseDate.setText(DateUtils.getYearDate(popularMovieDTO.getReleaseDate()));
        activityDetailBinding.tvDuration.setText("N/I");
        activityDetailBinding.tvVoteAverage.setText(String.valueOf(popularMovieDTO.getVoteAverage()));
        activityDetailBinding.tvOverview.setText(popularMovieDTO.getOverview());

        TheMovieDBService.processImage(this, popularMovieDTO, activityDetailBinding.imvPoster);

    }

    private void executeRequest(final PopularMovieDTO popularMovieDTO) {
        if(NetWorkUtils.isOnline(this)) {
            new ListTrailersAsyncTaskExecutor(this).execute(popularMovieDTO);
        } else {
            View view = findViewById(R.id.activity_detail);
            Snackbar snackbar =
                    Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.label_retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeRequest(popularMovieDTO);
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
    public void processFinish(List<TrailerModel> trailerModels) {

        mountListTrailersItens(trailerModels);

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
