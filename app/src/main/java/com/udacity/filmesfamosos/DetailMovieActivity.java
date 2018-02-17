package com.udacity.filmesfamosos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.udacity.filmesfamosos.Adapter.CustomRecycleViewOnClickListener;
import com.udacity.filmesfamosos.Adapter.TrailerRecycleViewAdapter;
import com.udacity.filmesfamosos.databinding.ActivityDetailBinding;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.ReviewModel;
import com.udacity.filmesfamosos.model.TrailerModel;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.repository.FavoriteMovieService;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.tasks.AsyncTaskDelegate;
import com.udacity.filmesfamosos.tasks.ListReviewsAsyncTaskExecutor;
import com.udacity.filmesfamosos.tasks.ListTrailersAsyncTaskExecutor;
import com.udacity.filmesfamosos.utils.DateUtils;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class DetailMovieActivity extends AppCompatActivity implements AsyncTaskDelegate<List<Object>> {

    private PopularMovieDTO popularMovieDTO;
    private ProgressDialog progressDialog = null;
    private Button btFavorite;
    private static FilterEnum latestFilter;

    private ActivityDetailBinding activityDetailBinding;
    private FavoriteMovieService favoriteMovieService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.setTitle(R.string.activity_title_detail);

        Intent intent = this.getIntent();

        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        btFavorite = activityDetailBinding.btFavorite;
        favoriteMovieService = new FavoriteMovieService(this);

        if(null != intent.getExtras()) {
            popularMovieDTO = (PopularMovieDTO) intent.getExtras().get(PopularMovieDTO.POPULAR_MOVIE_DTO);
            latestFilter = (FilterEnum) intent.getExtras().get("LATEST_FILTER");
            setComponentsValues(popularMovieDTO);
        }

        btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFavoriteButton(popularMovieDTO);
            }
        });

        executeRequest(popularMovieDTO);

    }

    private void onClickFavoriteButton(PopularMovieDTO popularMovieDTO) {
        if(btFavorite.getText().equals(getString(R.string.bt_unmark_favorite))) {
            favoriteMovieService.remove(popularMovieDTO.getId());
            btFavorite.setText(R.string.bt_mark_favorite);
        } else {
            favoriteMovieService.add(popularMovieDTO);
            btFavorite.setText(R.string.bt_unmark_favorite);
        }
    }

    private void mountListTrailersItems(List<Object> trailerModels) {
        RecyclerView trailersRecycle = activityDetailBinding.lvTrailers;
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        trailersRecycle.setLayoutManager(layout);

        trailersRecycle.setAdapter(new TrailerRecycleViewAdapter(this, trailerModels, new CustomRecycleViewOnClickListener<TrailerModel>() {
            @Override
            public void onClick(TrailerModel trailerModel) {
                launcherTrailerIntentView(trailerModel);
            }

        }));

    }

    private void mountListReviewsItems(List<Object> trailerModels) {
        RecyclerView reviewsRecycle = activityDetailBinding.lvTrailers;
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        reviewsRecycle.setLayoutManager(layout);

        reviewsRecycle.setAdapter(new TrailerRecycleViewAdapter(this, trailerModels, new CustomRecycleViewOnClickListener<TrailerModel>() {
            @Override
            public void onClick(TrailerModel trailerModel) {
                launcherTrailerIntentView(trailerModel);
            }

        }));

    }

    private void launcherTrailerIntentView(TrailerModel trailerModel) {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("v", trailerModel.getKey());
        URL url = NetWorkUtils.makeUrl(TheMovieDBService.YOUTUBE_VIEW_URL, null, queryParams);
        Intent intentPlay = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
        Intent chooser = Intent.createChooser(intentPlay , "Open With");

        if (intentPlay.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    private void setComponentsValues(final PopularMovieDTO popularMovieDTO) {

        verifyTextFavoriteButton(popularMovieDTO);

        activityDetailBinding.tvOriginalTitle.setText(popularMovieDTO.getOriginalTitle());
        activityDetailBinding.tvReleaseDate.setText(DateUtils.getYearDate(popularMovieDTO.getReleaseDate()));
        activityDetailBinding.tvDuration.setText("N/I");
        activityDetailBinding.tvVoteAverage.setText(String.valueOf(popularMovieDTO.getVoteAverage()));
        activityDetailBinding.tvOverview.setText(popularMovieDTO.getOverview());

        TheMovieDBService.processImage(this, popularMovieDTO, activityDetailBinding.imvPoster);

    }

    private void verifyTextFavoriteButton(final PopularMovieDTO popularMovieDTO) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(favoriteMovieService.isFavorite(popularMovieDTO)) {
                    btFavorite.setText(R.string.bt_unmark_favorite);
                } else {
                    btFavorite.setText(R.string.bt_mark_favorite);
                }
            }
        });
    }

    private void executeRequest(final PopularMovieDTO popularMovieDTO) {
        if(NetWorkUtils.isOnline(this)) {
            final Executor executor = Executors.newSingleThreadExecutor();
            new ListTrailersAsyncTaskExecutor(this).executeOnExecutor(executor, popularMovieDTO);
            new ListReviewsAsyncTaskExecutor(this).executeOnExecutor(executor, popularMovieDTO);
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
    public void processFinish(List<Object> listObject) {

        if (null != listObject && !listObject.isEmpty()) {
            if (listObject.get(0) instanceof TrailerModel) {
                mountListTrailersItems(listObject);
            } else if(listObject.get(0) instanceof ReviewModel) {
                mountListReviewsItems(listObject);
            }
        }

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
