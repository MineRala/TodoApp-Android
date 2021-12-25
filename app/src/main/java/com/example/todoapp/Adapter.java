package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    List<Model> tasksList;
    Listener listener;

    interface Listener {
        void onItemDeleteClicked(String itemId);
        void onItemClicked(int position);
        void onItemDone(String itemId);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Adapter(Context context, List<Model> tasksList ) {
        this.context = context;
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (tasksList.size() != 0) {
            holder.title.setText(tasksList.get(position).getTitle());
            holder.description.setText(tasksList.get(position).getDescription());
            holder.category.setText(tasksList.get(position).getCategory());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(position);
                }
            });
        } else {
            holder.itemView.setVisibility(View.INVISIBLE);
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemDeleteClicked(tasksList.get(holder.getBindingAdapterPosition()).getId());
                    tasksList.remove(holder.getBindingAdapterPosition());
                    notifyItemRemoved(holder.getBindingAdapterPosition());
                }
            }
        });

        holder.todoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listener.onItemDone(tasksList.get(holder.getBindingAdapterPosition()).getId());
                    tasksList.remove(holder.getBindingAdapterPosition());
                    notifyItemRemoved(holder.getBindingAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, category;
        ImageView deleteButton;
        CardView cardView;
        CheckBox todoCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            description = itemView.findViewById(R.id.taskDescription);
            description.setVisibility(View.INVISIBLE);
            category = itemView.findViewById(R.id.taskCategory);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            cardView = itemView.findViewById(R.id.cardView);
            todoCheckBox = itemView.findViewById(R.id.todoCheckBox);
        }
    }

    public void updateList(List<Model> updateList ) {
        this.tasksList.clear();
    }


}
