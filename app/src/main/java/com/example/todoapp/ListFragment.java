package com.example.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Adapter adapter;
    List<Model> tasksList;
    Database database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateList(database.getIsNotDoneTasks());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        MainActivity.getInstance().bottomNavigationView.setVisibility(View.VISIBLE);
        fab = view.findViewById(R.id.fab);

        tasksList = new ArrayList<>();
        database = new Database(getContext());
        recyclerView = view.findViewById(R.id.tasksRecyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter(getContext());
        adapter.updateList(database.getIsNotDoneTasks());
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),AddActivity.class);
            startActivity(intent);
        });

        adapter.setListener(new Adapter.Listener() {
            @Override
            public  void onItemDeleteClicked(String itemId, int position) {
               DialogExt.confirmDialog(itemId,position,getActivity(),database,adapter,database.getIsNotDoneTasks());
            }

            @Override
            public void onItemClicked(Model task) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("detail", task);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemDone(String itemId, int isChecked) {
                database.taskDone(itemId,isChecked);
            }
        });
        return view;
    }

    void deleteAllTasks() {
        Database database = new Database(getActivity());
        database.deleteAllData();
        adapter.clearData();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.doneTasks:
                Intent intent = new Intent(getActivity(), DoneActivity.class);
                getActivity().startActivity(intent);
                return true;
            case R.id.deleteAll:
                deleteAllTasks();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}