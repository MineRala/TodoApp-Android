package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    List<Model> tasksList;
    Model task;
    Listener listener;

    interface Listener {
        void onItemDeleteClicked(String itemId, int position);
        void onItemClicked(Model model);
        void onItemDone(String itemId, int isChecked);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Adapter(Context context ) {
        this.context = context;
        this.tasksList = new ArrayList<>();
        this.task  = new Model("","","","");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            Model task = tasksList.get(position);
            holder.title.setText(task.getTitle());
            holder.description.setText(task.getDescription());
            holder.time.setText(formatDate(task.getTimestamp()));
            holder.category.setText(task.getCategory());

            if (task.getIsDone() == 1) {
                holder.todoCheckBox.setChecked(true);
            } else {
                holder.todoCheckBox.setChecked(false);
            }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(task);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemDeleteClicked(tasksList.get(holder.getBindingAdapterPosition()).getId(), holder.getBindingAdapterPosition());
                    // tasksList.remove(holder.getBindingAdapterPosition());
                   // notifyItemRemoved(holder.getBindingAdapterPosition());
                }
            }
        });

        holder.todoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listener.onItemDone(tasksList.get(holder.getBindingAdapterPosition()).getId(), 1);
                } else {
                    listener.onItemDone(tasksList.get(holder.getBindingAdapterPosition()).getId(), 0);
                }
                tasksList.remove(holder.getBindingAdapterPosition());
                notifyItemRemoved(holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        }
        catch (ParseException e) { }

        return "";
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, category, time;
        ImageView deleteButton;
        CardView cardView;
        CheckBox todoCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            description = itemView.findViewById(R.id.taskDescription);
            description.setVisibility(View.INVISIBLE);
            category = itemView.findViewById(R.id.taskCategory);
            time = itemView.findViewById(R.id.taskTime);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            cardView = itemView.findViewById(R.id.cardView);
            todoCheckBox = itemView.findViewById(R.id.todoCheckBox);
        }
    }

    public void deleteItem(int position) {
        tasksList.remove(position);
        notifyItemRemoved(position);
    }


    public void updateList(List<Model> updateList) {
        this.tasksList.clear();
        this.tasksList.addAll(updateList);
    }

    public void clearData() {
        this.tasksList.clear();
    }
}
