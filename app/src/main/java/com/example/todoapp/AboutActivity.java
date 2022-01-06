package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class AboutActivity extends AppCompatActivity {
    private TextView textView;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
       // Toolbar toolbar =  findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       // setTitle(R.string.about);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.textViewAbout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.background));


        textView.setText(R.string.about_text);
    }
}