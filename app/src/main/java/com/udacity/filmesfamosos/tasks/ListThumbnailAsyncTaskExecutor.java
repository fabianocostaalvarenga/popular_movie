package com.udacity.filmesfamosos.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import com.udacity.filmesfamosos.Adapter.ImageAdapter;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;
import com.udacity.filmesfamosos.utils.ApplicationUtils;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class ListThumbnailAsyncTaskExecutor extends AsyncTask<Void, Integer, List<PopularMovieDTO>> {

    private ProgressDialog progressDialog = null;
    private Context context;
    private GridView view;
    private FilterEnum filterEnum;
    private ConnectivityManager connectivityManager;
    private static final String METADATA_API_KEY = "com.themoviesdb.api.key";
    private String apiKey;

    public ListThumbnailAsyncTaskExecutor(Context context, GridView view, FilterEnum filterEnum, ConnectivityManager connectivityManager) {
        this.context = context;
        this.view = view;
        this.filterEnum = filterEnum;
        this.connectivityManager = connectivityManager;
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

        List<PopularMovieDTO> result = null;
        if(NetWorkUtils.isOnline(connectivityManager)) {
            result = TheMovieDBService.listMoviesBy(filterEnum, apiKey);
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<PopularMovieDTO> popularMovieDTOs) {
        view.setAdapter(new ImageAdapter(context, popularMovieDTOs));

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        if(popularMovieDTOs.size() == 0) {
            Toast.makeText(this.context, "No internet connectivity...", Toast.LENGTH_LONG).show();
        }

        super.onPostExecute(popularMovieDTOs);
    }

}
