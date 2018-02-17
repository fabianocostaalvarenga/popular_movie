package com.udacity.filmesfamosos.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.udacity.filmesfamosos.model.ReviewModel;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.ApplicationUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 17/02/18.
 */

public class ListReviewsAsyncTaskExecutor extends AsyncTask<PopularMovieDTO, Integer, List<ReviewModel>> {

    private Context context;
    private static final String METADATA_API_KEY = "com.themoviesdb.api.key";
    private String apiKey;

    private AsyncTaskDelegate delegate = null;

    public ListReviewsAsyncTaskExecutor(AsyncTaskDelegate responder) {
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
    protected List<ReviewModel> doInBackground(PopularMovieDTO... movieDTOs) {
        apiKey = ApplicationUtils.getMetaDataValue(context, METADATA_API_KEY);
        List<ReviewModel> result = TheMovieDBService.listReviewsByMovie(movieDTOs[0], apiKey);
        return result;
    }

    @Override
    protected void onPostExecute(List<ReviewModel> reviewModels) {
        super.onPostExecute(reviewModels);
        if(null != delegate) {
            delegate.processFinish(reviewModels);
        }
    }

}
