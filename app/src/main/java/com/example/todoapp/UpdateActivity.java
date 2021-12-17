package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    EditText titleText, descriptionText, categoryText;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.update_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleText = findViewById(R.id.editTextTitle);
        descriptionText = findViewById(R.id.editTextDesc);
        categoryText = findViewById(R.id.editTextCategory);
        updateButton = findViewById(R.id.buttonUpdate);
    }
}