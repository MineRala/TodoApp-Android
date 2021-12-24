package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    List<Model> tasksList;
    ItemClickListener itemClickListener;


    public Adapter(Context context, List<Model> tasksList, ItemClickListener itemClickListener ) {
        this.context = context;
        this.tasksList = tasksList;
        this.itemClickListener = itemClickListener;
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
            holder.description.setText(tasksList.get(position).getDesc());
            holder.category.setText(tasksList.get(position).getCategory());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("detail", tasksList.get(position));
                    context.startActivity(intent);
                }
            });
        } else {
            holder.itemView.setVisibility(View.INVISIBLE);
        }

        holder.deleteButton.setOnClickListener(v -> {
            itemClickListener.onItemClicked(tasksList.get(holder.getBindingAdapterPosition()).getId());
            tasksList.remove(holder.getBindingAdapterPosition());
            notifyItemRemoved(holder.getBindingAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, category;
        ImageView deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            description = itemView.findViewById(R.id.taskDescription);
            description.setVisibility(View.INVISIBLE);
            category = itemView.findViewById(R.id.taskCategory);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public void updateList(List<Model> updateList ) {
        this.tasksList.clear();
    }

    public interface ItemClickListener {
        public void onItemClicked(String itemId);
    }

}
