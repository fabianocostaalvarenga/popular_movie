package com.udacity.filmesfamosos.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.filmesfamosos.R;

/**
 * Created by fabiano.alvarenga on 17/02/18.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public final TextView author;
    public final TextView content;
    public final TextView url;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        this.author = (TextView) itemView.findViewById(R.id.tv_author);
        this.content = (TextView) itemView.findViewById(R.id.tv_content);
        this.url = (TextView) itemView.findViewById(R.id.tv_url);
    }

}
