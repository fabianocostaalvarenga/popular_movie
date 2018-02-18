package com.udacity.filmesfamosos.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.udacity.filmesfamosos.R;
import com.udacity.filmesfamosos.model.dto.PopularMovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/15/17.
 */

public class ImageAdapter extends BaseAdapter {

    private static final String TAG = "ImageAdapter";

    private Context context;
    private List<PopularMovieDTO> popularMovieDTOs;
    private static LayoutInflater inflater = null;

    private Picasso picasso;
    private RequestCreator requestCreator;

    public ImageAdapter(Context context, List<PopularMovieDTO> iImages) {
        this.context = context;
        this.popularMovieDTOs = iImages;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.picasso = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View imgView = this.inflater.inflate(R.layout.activity_principal, null);
        ImageView imageView = (ImageView) imgView.findViewById(R.id.imageView);
        URL url = null;

        try {
            url = TheMovieDBService.getUrlThumbnail(popularMovieDTOs.get(position));
            this.requestCreator = this.picasso.load(String.valueOf(url.toURI()));
        } catch (URISyntaxException e) {
            Log.e(TAG, "Load image from url failed... {"+url.toString()+"}");
            e.printStackTrace();
        }

        this.requestCreator.error(R.mipmap.ic_launcher_round).into(imageView);

        return imgView;
    }

    @Override
    public int getCount() {
        if(null != popularMovieDTOs) {
            return popularMovieDTOs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(null != popularMovieDTOs) {
            return popularMovieDTOs.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
