package com.udacity.filmesfamosos.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.filmesfamosos.R;
import com.udacity.filmesfamosos.model.dto.MovieDTO;
import com.udacity.filmesfamosos.service.TheMovieDBService;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by fabiano.alvarenga on 10/15/17.
 */

public class ImageAdapter extends BaseAdapter implements Parcelable {

    private static final String TAG = "ImageAdapter";

    private Context context;
    private List<MovieDTO> movieDTOs;
    private static LayoutInflater inflater = null;

    private Picasso picasso;

    public ImageAdapter(Context context, List<MovieDTO> iImages) {
        this.context = context;
        this.movieDTOs = iImages;
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
            url = TheMovieDBService.getUrlThumbnail(movieDTOs.get(position));
            this.picasso
                    .load(String.valueOf(url.toURI()))
                    .resize(342,513)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Load image from url failed... {"+url.toString()+"}");
            e.printStackTrace();
        }

        return imgView;
    }

    @Override
    public int getCount() {
        if(null != movieDTOs) {
            return movieDTOs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(null != movieDTOs) {
            return movieDTOs.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    protected ImageAdapter(Parcel in) {
        movieDTOs = in.createTypedArrayList(MovieDTO.CREATOR);
    }

    public static final Creator<ImageAdapter> CREATOR = new Creator<ImageAdapter>() {
        @Override
        public ImageAdapter createFromParcel(Parcel in) {
            return new ImageAdapter(in);
        }

        @Override
        public ImageAdapter[] newArray(int size) {
            return new ImageAdapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(movieDTOs);
    }
}
