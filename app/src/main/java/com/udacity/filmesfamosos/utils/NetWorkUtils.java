package com.udacity.filmesfamosos.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class NetWorkUtils {

    private static final String TAG = "NetWorkUtils";

    public static URL makeUrl(String urlBase, String urlPath, Map<String, String> queryParameter) {
        URL urlForRequest = null;
        try {
            Uri.Builder builder = Uri.parse(urlBase).buildUpon();

            if(null != urlPath) {
                builder.appendPath(urlPath.replace("/",""));
            }

            if(null != queryParameter && queryParameter.size() > 0) {
                for (Map.Entry<String, String> element : queryParameter.entrySet()) {
                    builder.appendQueryParameter(element.getKey(), element.getValue());
                }
            }

            Uri builtUri = builder.build();

            urlForRequest = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return urlForRequest;
    }

    public static InputStream requestMovies(URL url) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            Log.e(TAG, "FALHA AO CONECTAR AO SERVIDOR...");
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return inputStream;
    }

    public static Boolean isOnline(ConnectivityManager connectivityManager) {
        ConnectivityManager cm = connectivityManager;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
