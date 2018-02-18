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
import android.widget.ImageView;

import com.udacity.filmesfamosos.Adapter.CustomRecycleViewOnClickListener;
import com.udacity.filmesfamosos.Adapter.ReviewsRecycleViewAdapter;
import com.udacity.filmesfamosos.Adapter.TrailerRecycleViewAdapter;
import com.udacity.filmesfamosos.databinding.ActivityDetailBinding;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.ReviewModel;
import com.udacity.filmesfamosos.model.TrailerModel;
import com.udacity.filmesfamosos.model.dto.DetailsResultTaskDTO;
import com.udacity.filmesfamosos.model.dto.MovieDTO;
import com.udacity.filmesfamosos.repository.FavoriteMovieService;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.tasks.AsyncTaskDelegate;
import com.udacity.filmesfamosos.tasks.DetailsAsyncTaskExecutor;
import com.udacity.filmesfamosos.utils.DateUtils;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class DetailMovieActivity extends AppCompatActivity implements AsyncTaskDelegate<DetailsResultTaskDTO> {

    private MovieDTO movieDTO;
    private ProgressDialog progressDialog = null;
    private Button btFavorite;
    private static FilterEnum latestFilter;
    private ImageView imgSharing;
    private TrailerModel firstTrilerModel;

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
        imgSharing = activityDetailBinding.ivArrowSharing;

        favoriteMovieService = new FavoriteMovieService(this);

        if(null != intent.getExtras()) {
            movieDTO = (MovieDTO) intent.getExtras().get(MovieDTO.POPULAR_MOVIE_DTO);
            latestFilter = (FilterEnum) intent.getExtras().get("LATEST_FILTER");
            setComponentsValues(movieDTO);
        }

        btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFavoriteButton(movieDTO);
            }
        });

        imgSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherSharTrailerIntentView(firstTrilerModel);
            }
        });

        executeRequest(movieDTO);

    }

    private void onClickFavoriteButton(MovieDTO movieDTO) {
        if(btFavorite.getText().equals(getString(R.string.bt_unmark_favorite))) {
            favoriteMovieService.remove(movieDTO.getId());
            btFavorite.setText(R.string.bt_mark_favorite);
        } else {
            favoriteMovieService.add(movieDTO);
            btFavorite.setText(R.string.bt_unmark_favorite);
        }
    }

    private void mountListTrailersItems(List<TrailerModel> trailerModels) {
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

    private void mountListReviewsItems(List<ReviewModel> reviewModels) {
        RecyclerView reviewsRecycle = activityDetailBinding.lvReviews;
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        reviewsRecycle.setLayoutManager(layout);

        reviewsRecycle.setAdapter(new ReviewsRecycleViewAdapter(this, reviewModels));

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

    private void launcherSharTrailerIntentView(TrailerModel trailerModel) {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("v", trailerModel.getKey());
        URL url = NetWorkUtils.makeUrl(TheMovieDBService.YOUTUBE_VIEW_URL, null, queryParams);

        Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, trailerModel.getName());
        share.putExtra(Intent.EXTRA_TEXT, url.toString());
        Intent chooser = Intent.createChooser(share , "Sharing With");

        if (share.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    private void setComponentsValues(final MovieDTO movieDTO) {

        verifyTextFavoriteButton(movieDTO);

        activityDetailBinding.tvOriginalTitle.setText(movieDTO.getOriginalTitle());
        activityDetailBinding.tvReleaseDate.setText(DateUtils.getYearDate(movieDTO.getReleaseDate()));
        activityDetailBinding.tvDuration.setText("N/I");
        activityDetailBinding.tvVoteAverage.setText(String.valueOf(movieDTO.getVoteAverage()));
        activityDetailBinding.tvOverview.setText(movieDTO.getOverview());

        TheMovieDBService.processImage(this, movieDTO, activityDetailBinding.imvPoster);

    }

    private void verifyTextFavoriteButton(final MovieDTO movieDTO) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(favoriteMovieService.isFavorite(movieDTO)) {
                    btFavorite.setText(R.string.bt_unmark_favorite);
                } else {
                    btFavorite.setText(R.string.bt_mark_favorite);
                }
            }
        });
    }

    private void executeRequest(final MovieDTO movieDTO) {
        if(NetWorkUtils.isOnline(this)) {
            new DetailsAsyncTaskExecutor(this).execute(movieDTO);
        } else {
            View view = findViewById(R.id.activity_detail);
            Snackbar snackbar =
                    Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.label_retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeRequest(movieDTO);
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
    public void processFinish(DetailsResultTaskDTO detailsResultTaskDTO) {

        if(null != detailsResultTaskDTO.getTrailerModelList()
                && !detailsResultTaskDTO.getTrailerModelList().isEmpty()) {
            this.firstTrilerModel = detailsResultTaskDTO.getTrailerModelList().get(0);
        }

        mountListTrailersItems(detailsResultTaskDTO.getTrailerModelList());
        mountListReviewsItems(detailsResultTaskDTO.getReviewModels());

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}