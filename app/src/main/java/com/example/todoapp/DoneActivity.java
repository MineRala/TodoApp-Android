package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class DoneActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    List<Model> tasksList;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.done);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        database = new Database(this);
        adapter = new Adapter(this);
        adapter.updateList(database.getIsDoneTasks());

        recyclerView = findViewById(R.id.tasksRecyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksList = new ArrayList<>();
        recyclerView.setAdapter(adapter);

        adapter.setListener(new Adapter.Listener() {
            @Override
            public void onItemDeleteClicked(String itemId) {
                database.deleteOneRow(itemId);
            }

            @Override
            public void onItemClicked(Model task) {
                Intent intent = new Intent(DoneActivity.this, DetailActivity.class);
                intent.putExtra("detail", task);
                startActivity(intent);
            }

            @Override
            public void onItemDone(String itemId, int isChecked) {
                database.taskDone(itemId,isChecked);
            }

        });
    }
}