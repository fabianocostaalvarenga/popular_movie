package com.udacity.filmesfamosos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.DateUtils;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class DetailMovieActivity extends AppCompatActivity {

    private PopularMovieDTO popularMovieDTO;

    private TextView tvOriginalTitle;
    private ImageView imvPoster;
    private TextView tvReleaseDate;
    private TextView tvDuration;
    private TextView tvVoteAverage;
    private Button btFavorite;
    private TextView tvOverview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.setTitle(R.string.activity_title_detail);

        Intent intent = this.getIntent();

        if(null != intent.getExtras()) {
            popularMovieDTO = (PopularMovieDTO) intent.getExtras().get(PopularMovieDTO.POPULAR_MOVIE_DTO);
            setComponentsValues(popularMovieDTO);
        }

    }

    private void setComponentsValues(PopularMovieDTO popularMovieDTO) {

        tvOriginalTitle = (TextView) findViewById(R.id.tv_originalTitle);
        tvReleaseDate = (TextView) findViewById(R.id.tv_releaseDate);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        tvVoteAverage = (TextView) findViewById(R.id.tv_vote_average);
        tvOverview = (TextView) findViewById(R.id.tv_overview);

        imvPoster = (ImageView) findViewById(R.id.imv_poster);

        btFavorite = (Button) findViewById(R.id.bt_favorite);

        tvOriginalTitle.setText(popularMovieDTO.getOriginalTitle());
        tvReleaseDate.setText(DateUtils.getYearDate(popularMovieDTO.getReleaseDate()));
        tvDuration.setText("N/I");
        tvVoteAverage.setText(String.valueOf(popularMovieDTO.getVoteAverage()));
        tvOverview.setText(popularMovieDTO.getOverview());

        TheMovieDBService.processImage(this, popularMovieDTO, imvPoster);

    }
}
