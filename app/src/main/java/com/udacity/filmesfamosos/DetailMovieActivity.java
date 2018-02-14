package com.udacity.filmesfamosos;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.filmesfamosos.Adapter.TrailerLineRecycleAdapter;
import com.udacity.filmesfamosos.databinding.ActivityDetailBinding;
import com.udacity.filmesfamosos.model.TrailerModel;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

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

        List<TrailerModel> trailerModels = getTrailerModelsFakeData();

        mountListTrailersItens(trailerModels);

    }

    @NonNull
    private List<TrailerModel> getTrailerModelsFakeData() {
        List<TrailerModel> trailerModels = new ArrayList<TrailerModel>();
        trailerModels.add(
                new TrailerModel("533ec654c3a36854480003eb", "en", "US", "SUXWAEX2jlg", "Batman v Superman: Dawn of Justice", "YouTube", 720, "Trailer")
        );
        trailerModels.add(
                new TrailerModel("533ec654c3a36854480003ec", "en", "US", "SUXWAEX2jlh", "A Falha de San Andreas", "YouTube", 720, "Trailer")
        );
        trailerModels.add(
                new TrailerModel("533ec654c3a36854480003ed", "en", "US", "SUXWAEX2jli", "O Espetacular Homem Aranha", "YouTube", 720, "Trailer")
        );
        trailerModels.add(
                new TrailerModel("533ec654c3a36854480003ef", "en", "US", "SUXWAEX2jlj", "Batman v Superman: Dawn of Justice", "YouTube", 720, "Trailer")
        );
        trailerModels.add(
                new TrailerModel("533ec654c3a36854480003eg", "en", "US", "SUXWAEX2jll", "A Falha de San Andreas", "YouTube", 720, "Trailer")
        );
        trailerModels.add(
                new TrailerModel("533ec654c3a36854480003eh", "en", "US", "SUXWAEX2jlm", "O Espetacular Homem Aranha", "YouTube", 720, "Trailer")
        );
        return trailerModels;
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
}
