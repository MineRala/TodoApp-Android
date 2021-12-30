package com.example.todoapp;

import android.content.Intent;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        MainActivity.getInstance().bottomNavigationView.setVisibility(View.VISIBLE);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.tasksRecyclerListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tasksList = new ArrayList<>();
        database = new Database(getContext());

        adapter = new Adapter(getContext());
        if (database.getIsNotDoneTasks().size() != 0) {
            adapter.updateList(database.getIsNotDoneTasks());
        }
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
            public  void onItemDeleteClicked(String itemId) {
              // return confirmDialog(itemId);
                database.deleteOneRow(itemId);
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
/*
    public boolean confirmDialog(String itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Confirmation" );
        builder.setMessage("Are you sure you want to delete task ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.deleteOneRow(itemId);

            }

        });
        return true;
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return false;
        builder.create().show();
       if (builder.setPositiveButton())
    }
*/
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
                adapter.getFilter().filter(newText);
                 return true;
            }
        });
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