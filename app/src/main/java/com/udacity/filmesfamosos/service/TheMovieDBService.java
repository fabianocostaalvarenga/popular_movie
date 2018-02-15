package com.udacity.filmesfamosos.service;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.udacity.filmesfamosos.R;
import com.udacity.filmesfamosos.model.FilterEnum;
import com.udacity.filmesfamosos.model.TrailerModel;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.utils.NetWorkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class TheMovieDBService {

    private static final String TAG = "TheMovieDBConsumer";

    private static final String API_THEMOVIEDB_BASE_URL =
            "https://api.themoviedb.org/3/movie";

    private static final String API_TMDB_BASE_URL =
            "https://image.tmdb.org/t/p/w342";

    public static final String YOUTUBE_VIEW_URL =
            "https://www.youtube.com/watch"; //?v=Wfql_DoHRKc

    public static List<PopularMovieDTO> listMoviesBy(String apiKey) {
        return listMoviesBy(null, apiKey);
    }

    public static List<PopularMovieDTO> listMoviesBy(FilterEnum filterEnum, String apiKey) {

        Map<String, String> queryParameter = new HashMap<>();
        queryParameter.put("api_key", apiKey);

        URL url = NetWorkUtils.makeUrl(API_THEMOVIEDB_BASE_URL, filterEnum.getFilter(), queryParameter);
        InputStream inputStream = NetWorkUtils.requestMovies(url);

        return listPopularMovieDTO(inputStream);
    }

    public static List<TrailerModel> listTrailersByMovie(PopularMovieDTO popularMovieDTO, String apiKey) {

        Map<String, String> queryParameter = new HashMap<>();
        queryParameter.put("api_key", apiKey);

        String urlPath = String.valueOf(popularMovieDTO.getId()) + "/videos";
        URL url = NetWorkUtils.makeUrl(API_THEMOVIEDB_BASE_URL, urlPath, queryParameter);
        InputStream inputStream = NetWorkUtils.requestMovies(url);

        return listTrailersModel(inputStream);
    }

    public static URL getUrlForThumbnail(PopularMovieDTO popularMovieDTO) {
        return NetWorkUtils.makeUrl(API_TMDB_BASE_URL, popularMovieDTO.getPosterPath(), null);
    }

    private static List<PopularMovieDTO> listPopularMovieDTO(InputStream in) {
        BufferedReader streamReader = null;
        List<PopularMovieDTO> result = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            JSONArray arr = jsonObject.getJSONArray("results");

            Type listType = new TypeToken<List<PopularMovieDTO>>() {}.getType();

            result = new Gson().fromJson(String.valueOf(arr), listType);

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return result;

    }

    private static List<TrailerModel> listTrailersModel(InputStream in) {
        BufferedReader streamReader = null;
        List<TrailerModel> result = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            JSONArray arr = jsonObject.getJSONArray("results");

            Type listType = new TypeToken<List<TrailerModel>>() {}.getType();

            result = new Gson().fromJson(String.valueOf(arr), listType);

        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return result;

    }

    public static void processImage(Context context, PopularMovieDTO popularMovieDTO, ImageView imageView) {
        URL url = getUrlForThumbnail(popularMovieDTO);
        try {
            RequestCreator requestCreator = Picasso.with(context).load(String.valueOf(url.toURI()));
            requestCreator.error(R.mipmap.ic_launcher_round).into(imageView);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Load image from url failed... {"+url.toString()+"}");
            e.printStackTrace();
        }
    }
}
