package com.udacity.filmesfamosos.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.reflect.TypeToken;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.ReviewModel;
import com.udacity.filmesfamosos.model.TrailerModel;
import com.udacity.filmesfamosos.model.dto.DetailsResultTaskDTO;
import com.udacity.filmesfamosos.model.dto.MovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.ApplicationUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 18/02/18.
 */

public class DetailsAsyncTaskExecutor extends AsyncTask<MovieDTO, Integer, DetailsResultTaskDTO> {

    private Context context;
    private static final String METADATA_API_KEY = "com.themoviesdb.api.key";
    private String apiKey;

    private AsyncTaskDelegate delegate = null;

    public DetailsAsyncTaskExecutor(AsyncTaskDelegate responder) {
        this.context =  ((Activity)responder).getApplicationContext();
        this.delegate = responder;
    }

    @Override
    protected void onPreExecute() {
        if(null != delegate) {
            delegate.processStart();
        }
    }

    @Override
    protected DetailsResultTaskDTO doInBackground(MovieDTO... movieDTOs) {
        apiKey = ApplicationUtils.getMetaDataValue(context, METADATA_API_KEY);
        List<ReviewModel> reviewModels = (List<ReviewModel>) (Object) TheMovieDBService.requestTheMovieDBApi(apiKey, FilterEnum.REVIEWS, movieDTOs[0].getId(),  new TypeToken<List<ReviewModel>>() {}.getType());
        List<TrailerModel> trailerModels = (List<TrailerModel>) (Object) TheMovieDBService.requestTheMovieDBApi(apiKey, FilterEnum.VIDEOS, movieDTOs[0].getId(), new TypeToken<List<TrailerModel>>() {}.getType());
        return new DetailsResultTaskDTO(trailerModels, reviewModels);
    }

    @Override
    protected void onPostExecute(DetailsResultTaskDTO detailsResultTaskDTOs) {
        super.onPostExecute(detailsResultTaskDTOs);
        if(null != delegate) {
            delegate.processFinish(detailsResultTaskDTOs);
        }
    }

}
