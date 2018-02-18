package com.udacity.filmesfamosos.service;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.udacity.filmesfamosos.R;
import com.udacity.filmesfamosos.model.FilterEnum;
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
            "https://www.youtube.com/watch";

    public static List<Class> requestTheMovieDBApi(String apiKey, FilterEnum filterEnum, Long movieId, Type type) {

        Map<String, String> queryParameter = new HashMap<>();
        queryParameter.put("api_key", apiKey);

        URL url = NetWorkUtils.makeUrl(API_THEMOVIEDB_BASE_URL, filterEnum.getFilter(movieId), queryParameter);
        InputStream inputStream = NetWorkUtils.launchRequest(url);

        return extractListValues(inputStream, type);
    }

    private static List<Class> extractListValues(InputStream in, Type listType) {
        BufferedReader streamReader = null;
        List<Class> result = null;
        try {
            streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            JSONArray arr = jsonObject.getJSONArray("results");

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
        } finally {
            try {
                in.close();
                streamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    public static URL getUrlThumbnail(PopularMovieDTO popularMovieDTO) {
        return NetWorkUtils.makeUrl(API_TMDB_BASE_URL, popularMovieDTO.getPosterPath(), null);
    }

    public static void processImage(Context context, PopularMovieDTO popularMovieDTO, ImageView imageView) {
        URL url = getUrlThumbnail(popularMovieDTO);
        try {
            RequestCreator requestCreator = Picasso.with(context).load(String.valueOf(url.toURI()));
            requestCreator.error(R.mipmap.ic_launcher_round).into(imageView);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Load image from url failed... {"+url.toString()+"}");
            e.printStackTrace();
        }
    }

}
