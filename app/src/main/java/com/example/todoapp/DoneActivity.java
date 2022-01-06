package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.task_done);
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
            public void onItemDeleteClicked(String itemId, int position) {
                DialogExt.confirmDialog(itemId, position, DoneActivity.this, database, adapter, database.getIsDoneTasks());
            }

            @Override
            public void onItemClicked(Model task) {
                Intent intent = new Intent(DoneActivity.this, DetailActivity.class);
                intent.putExtra("detail", task);
                startActivity(intent);
            }

            @Override
            public void onItemDone(String itemId, int isChecked) {
                database.taskDone(itemId, isChecked);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateList(database.getIsDoneTasks());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoneActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
