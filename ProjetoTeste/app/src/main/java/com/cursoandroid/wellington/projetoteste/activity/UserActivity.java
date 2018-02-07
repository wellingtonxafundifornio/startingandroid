package com.cursoandroid.wellington.projetoteste.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.cursoandroid.wellington.projetoteste.R;
import com.cursoandroid.wellington.projetoteste.adapter.TasksAdapter;
import com.cursoandroid.wellington.projetoteste.data.SessionHandler;
import com.cursoandroid.wellington.projetoteste.model.Task;
import com.cursoandroid.wellington.projetoteste.model.User;
import com.cursoandroid.wellington.projetoteste.web.WebTaskTodo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

/**
 * Created by usuario on 31/01/18.
 */

public class UserActivity extends AppCompatActivity{

    private User user;

    RecyclerView myList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second); //layout that contain recyclerview

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myList = findViewById(R.id.recycler_tasks);
        myList.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL,
                false));
        myList.setAdapter(new TasksAdapter(this));

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();

        SessionHandler sessionHandler = new SessionHandler();
        try{
            user = sessionHandler.getUser(this);
        }catch (RuntimeException x){
            sessionHandler.removeSession(this);
            Intent myIntent = new Intent(getApplication(), LoginActivity.class);
            startActivity(myIntent);
            return;
        }

        EventBus.getDefault().register(this);

        initializeScreenFields();

        WebTaskTodo myCall = new WebTaskTodo(this, user.getToken());
        myCall.execute();
    }

    private void initializeScreenFields() {
        getSupportActionBar().setTitle(getString(R.string.label_tasks, user.getUsername()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void handleError(Error error){
        Log.d("", error.getMessage());
    }

    @Subscribe
    public void handleList(List<Task> taskList){
        ( (TasksAdapter) myList.getAdapter() ).setTaskList(taskList);
        myList.getAdapter().notifyDataSetChanged();
    }
}
