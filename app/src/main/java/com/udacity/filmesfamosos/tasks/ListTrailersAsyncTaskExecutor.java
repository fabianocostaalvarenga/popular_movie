package com.udacity.filmesfamosos.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.udacity.filmesfamosos.model.TrailerModel;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.ApplicationUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 14/02/18.
 */

public class ListTrailersAsyncTaskExecutor extends AsyncTask<PopularMovieDTO, Integer, List<TrailerModel>> {

    private Context context;
    private static final String METADATA_API_KEY = "com.themoviesdb.api.key";
    private String apiKey;

    private AsyncTaskDelegate delegate = null;

    public ListTrailersAsyncTaskExecutor(AsyncTaskDelegate responder) {
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
    protected List<TrailerModel> doInBackground(PopularMovieDTO... movieDTOs) {
        apiKey = ApplicationUtils.getMetaDataValue(context, METADATA_API_KEY);
        List<TrailerModel> result = TheMovieDBService.listTrailersByMovie(movieDTOs[0], apiKey);
        return result;
    }

    @Override
    protected void onPostExecute(List<TrailerModel> trailerModels) {
        super.onPostExecute(trailerModels);
        if(null != delegate) {
            delegate.processFinish(trailerModels);
        }
    }
}
