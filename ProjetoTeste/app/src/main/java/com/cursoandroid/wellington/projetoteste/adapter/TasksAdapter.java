package com.cursoandroid.wellington.projetoteste.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoandroid.wellington.projetoteste.R;
import com.cursoandroid.wellington.projetoteste.model.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wellington on 31/01/18.
 */

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder>{

    private final Context context;

    List<Task> taskList;

    public TasksAdapter(Context context){
        this.context = context;
        taskList = new ArrayList<>();
    }

    public List<Task> getTaskList(){
        return taskList;
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
    }


    //inflate the view and its view holder
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.row_task, parent, false);

        //Return a new holder instance
        TaskViewHolder viewHolder = new TaskViewHolder(contactView);
        return viewHolder;
    }

    //bind data to the view.
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.labelName.setText(task.getName());
        holder.labelDescription.setText(task.getDescription());

        Picasso.with(context).load(task.getImage()).into(holder.imageTask);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
