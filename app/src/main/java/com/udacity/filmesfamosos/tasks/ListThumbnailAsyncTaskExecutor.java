package com.udacity.filmesfamosos.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

import com.udacity.filmesfamosos.Adapter.ImageAdapter;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.ApplicationUtils;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class ListThumbnailAsyncTaskExecutor extends AsyncTask<Void, Integer, List<PopularMovieDTO>> {

    private ProgressDialog progressDialog = null;
    private Context context;
    private GridView view;
    private FilterEnum filterEnum;
    private static final String METADATA_API_KEY = "com.themoviesdb.api.key";
    private String apiKey;

    public ListThumbnailAsyncTaskExecutor(Context context, GridView view, FilterEnum filterEnum) {
        this.context = context;
        this.view = view;
        this.filterEnum = filterEnum;
    }

    @Override
    protected void onPreExecute() {
        if(progressDialog != null) {
            progressDialog.cancel();
        }
        progressDialog = ProgressDialog.show(context, "", "Please wait...", true, true);
    }

    @Override
    protected List<PopularMovieDTO> doInBackground(Void... voids) {

        apiKey = ApplicationUtils.getMetaDataValue(context, METADATA_API_KEY);

        List<PopularMovieDTO> result = TheMovieDBService.listMoviesBy(filterEnum, apiKey);

        return result;
    }

    @Override
    protected void onPostExecute(List<PopularMovieDTO> popularMovieDTOs) {
        view.setAdapter(new ImageAdapter(context, popularMovieDTOs));

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        super.onPostExecute(popularMovieDTOs);
    }

}
