package com.example.todoapp;

import java.io.Serializable;

public class Model implements Serializable {
    private String id,title, description, category, timestamp;
    private int isDone;

    public Model(String id, String title, String description, String category, String timestamp, int isDone) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.timestamp = timestamp;
        this.isDone = isDone;
    }

    public Model(String id, String title, String description, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
