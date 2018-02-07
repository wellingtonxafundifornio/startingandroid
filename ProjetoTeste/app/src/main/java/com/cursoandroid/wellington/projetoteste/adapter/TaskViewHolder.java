package com.cursoandroid.wellington.projetoteste.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cursoandroid.wellington.projetoteste.R;

/**
 * Created by Wellington on 01/02/18.
 */

class TaskViewHolder extends RecyclerView.ViewHolder{

    TextView labelName;
    TextView labelDescription;
    ImageView imageTask;

    public TaskViewHolder(View itemView) {
        super(itemView);
        labelName = itemView.findViewById(R.id.label_name);
        labelDescription = itemView.findViewById(R.id.label_description);
        imageTask = itemView.findViewById(R.id.image_task);
    }
}
