package com.udacity.filmesfamosos;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.filmesfamosos.databinding.ActivityDetailBinding;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.DateUtils;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class DetailMovieActivity extends AppCompatActivity {

    private PopularMovieDTO popularMovieDTO;

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
}
