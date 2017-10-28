package com.udacity.filmesfamosos.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.ApplicationUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class ListThumbnailAsyncTaskExecutor
        extends AsyncTask<FilterEnum, Integer, List<PopularMovieDTO>> {

    private Context context;
    private static final String METADATA_API_KEY = "com.themoviesdb.api.key";
    private String apiKey;

    private AsyncTaskDelegate delegate = null;

    public ListThumbnailAsyncTaskExecutor(AsyncTaskDelegate responder) {
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
    protected List<PopularMovieDTO> doInBackground(FilterEnum... filterEnum) {
        apiKey = ApplicationUtils.getMetaDataValue(context, METADATA_API_KEY);
        List<PopularMovieDTO> result = TheMovieDBService.listMoviesBy(filterEnum[0], apiKey);
        return result;
    }

    @Override
    protected void onPostExecute(List<PopularMovieDTO> popularMovieDTOs) {
        super.onPostExecute(popularMovieDTOs);
        if(null != delegate) {
            delegate.processFinish(popularMovieDTOs);
        }
    }

}
