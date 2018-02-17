package com.udacity.filmesfamosos.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.filmesfamosos.R;
import com.udacity.filmesfamosos.model.TrailerModel;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 14/02/18.
 */

public class TrailerRecycleViewAdapter extends RecyclerView.Adapter {

    private List<Object> trailerModels;
    private Context context;
    private CustomRecycleViewOnClickListener listener;

    public TrailerRecycleViewAdapter(Context context, List<Object> trailerModels, CustomRecycleViewOnClickListener listener) {
        this.trailerModels = trailerModels;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_trailer_list, parent, false);

        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(null != trailerModels) {

            TrailerViewHolder viewHolder = (TrailerViewHolder) holder;

            final TrailerModel trailerModel = (TrailerModel) trailerModels.get(position);

            viewHolder.name.setText(trailerModel.getName());

            viewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(trailerModel);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if(null == trailerModels) {return 0;}
        return trailerModels.size();
    }

}
