package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateActivity extends AppCompatActivity {
    EditText titleText, descriptionText, categoryText;
    Button updateButton;
    ImageView  backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back);

        setSupportActionBar(toolbar);
        setTitle(R.string.update_task);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleText = findViewById(R.id.editTextTitle);
        descriptionText = findViewById(R.id.editTextDesc);
        categoryText = findViewById(R.id.editTextCategory);
        updateButton = findViewById(R.id.buttonUpdate);
       // backButton = findViewById(R.id.backButton);

    }
}