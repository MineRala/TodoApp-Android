package com.example.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        MainActivity.getInstance().bottomNavigationView.setVisibility(View.VISIBLE);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.tasksRecyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksList = new ArrayList<>();
        database = new Database(getContext());
        tasksList.addAll(database.getIsDoneTasks());
        adapter = new Adapter(getContext(),tasksList);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivity(intent);
            }
        });

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
                database.taskDone(itemId,1);
            }

        });

        return view;
    }

    void deleteAllTasks() {
        Database database = new Database(getActivity());
        database.deleteAllData();
        adapter.updateList(database.getIsDoneTasks());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list,menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem search = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_here));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // filtreleme burada yapılacak
                // adapter.getFilter().filter(newText)
                // return true;
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            return false;
        }
        if (id == R.id.deleteAll) {
          deleteAllTasks();
        }
        return super.onOptionsItemSelected(item);
    }

}