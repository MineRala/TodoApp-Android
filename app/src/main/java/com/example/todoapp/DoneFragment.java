package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class DoneFragment extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    List<Model> tasksList;
    Database database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_done, container, false);
        recyclerView = view.findViewById(R.id.tasksRecyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksList = new ArrayList<>();
        database = new Database(getContext());
        adapter = new Adapter(getContext(),tasksList);
        recyclerView.setAdapter(adapter);

        adapter.setListener(new Adapter.Listener() {
            @Override
            public void onItemDeleteClicked(String itemId) {
                database.deleteOneRow(itemId);
            }

            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("detail", tasksList.get(position));
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemDone(String itemId) {
                database.taskDone(itemId,0);
            }

        });
        return view;

    }
}