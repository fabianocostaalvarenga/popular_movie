package com.udacity.filmesfamosos.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.filmesfamosos.R;

import java.io.Serializable;

/**
 * Created by fabiano.alvarenga on 13/02/18.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements Serializable {

    public final TextView name;

    public TrailerViewHolder(View itemView, @NonNull View.OnClickListener listener) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.tv_trailer_title);
        this.name.setOnClickListener(listener);
    }

}
