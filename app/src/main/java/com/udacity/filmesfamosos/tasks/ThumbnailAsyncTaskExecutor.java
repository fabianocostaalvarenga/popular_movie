package com.udacity.filmesfamosos.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.reflect.TypeToken;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.MovieDTO;
import com.udacity.filmesfamosos.service.FavoriteMovieService;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.ApplicationUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class ThumbnailAsyncTaskExecutor
        extends AsyncTask<FilterEnum, Integer, List<MovieDTO>> {

    private Context context;
    private static final String METADATA_API_KEY = "com.themoviesdb.api.key";
    private String apiKey;

    private AsyncTaskDelegate delegate = null;
    private FavoriteMovieService favoriteMovieService;

    public ThumbnailAsyncTaskExecutor(AsyncTaskDelegate responder) {
        this.context =  ((Activity)responder).getApplicationContext();
        this.delegate = responder;
        this.favoriteMovieService = new FavoriteMovieService(context);
    }

    @Override
    protected void onPreExecute() {
        if(null != delegate) {
            delegate.processStart();
        }
    }

    @Override
    protected List<MovieDTO> doInBackground(FilterEnum... filterEnum) {

        List<MovieDTO> result = null;

        if(FilterEnum.FAVORITE.equals(filterEnum[0])){
            result = favoriteMovieService.getAll();
        } else {
            apiKey = ApplicationUtils.getMetaDataValue(context, METADATA_API_KEY);
            result = (List<MovieDTO>) (Object) TheMovieDBService.requestTheMovieDBApi(apiKey, filterEnum[0], null, new TypeToken<List<MovieDTO>>() {}.getType());
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<MovieDTO> movieDTOs) {
        super.onPostExecute(movieDTOs);
        if(null != delegate) {
            delegate.processFinish(movieDTOs);
        }
    }

}
