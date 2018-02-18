package com.udacity.filmesfamosos.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.filmesfamosos.R;
import com.udacity.filmesfamosos.model.ReviewModel;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 17/02/18.
 */

public class ReviewsRecycleViewAdapter extends RecyclerView.Adapter {

    private List<Object> reviewsModels;
    private Context context;

    public ReviewsRecycleViewAdapter(Context context, List<Object> reviewsModels) {
        this.reviewsModels = reviewsModels;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_reviews_list, parent, false);

        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(null != reviewsModels) {

            ReviewViewHolder viewHolder = (ReviewViewHolder) holder;

            final ReviewModel reviewModel = (ReviewModel) reviewsModels.get(position);

            viewHolder.author.setText(reviewModel.getAuthor());
            viewHolder.content.setText(reviewModel.getContent());
            viewHolder.url.setText(reviewModel.getUrl());

        }

    }

    @Override
    public int getItemCount() {
        if(null == reviewsModels) {return 0;}
        return reviewsModels.size();
    }

}
