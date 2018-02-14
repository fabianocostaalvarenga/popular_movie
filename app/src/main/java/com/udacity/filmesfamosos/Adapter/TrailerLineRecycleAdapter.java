package com.udacity.filmesfamosos.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.udacity.filmesfamosos.R;
import com.udacity.filmesfamosos.model.TrailerModel;

import java.util.List;

/**
 * Created by fabiano.alvarenga on 14/02/18.
 */

public class TrailerLineRecycleAdapter extends RecyclerView.Adapter {

    private List<TrailerModel> trailerModels;
    private Context context;
    private View.OnClickListener listener;

    public TrailerLineRecycleAdapter(Context context, List<TrailerModel> trailerModels, View.OnClickListener listener) {
        this.trailerModels = trailerModels;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_trailer_list, parent, false);

        TrailerViewHolder viewHolder = new TrailerViewHolder(view, listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TrailerViewHolder viewHolder = (TrailerViewHolder) holder;

        TrailerModel trailerModel = trailerModels.get(position);

        viewHolder.name.setText(trailerModel.getName());

    }

    @Override
    public int getItemCount() {
        return trailerModels.size();
    }

}
