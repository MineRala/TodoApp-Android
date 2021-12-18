package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    TextView titleText, descriptionText, categoryText;
    Model model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof Model) {
            model = (Model) object;
        }

        titleText = findViewById(R.id.titleTextView);
        descriptionText = findViewById(R.id.descriptionTextView);
        categoryText = findViewById(R.id.categoryTextView);

        if (model != null) {
            titleText.setText(model.getTitle());
            descriptionText.setText(model.getDesc());
            categoryText.setText(model.getCategory());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            Toast.makeText(this,"Share tapped",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (id == R.id.edit) {
            Toast.makeText(this,"Edit tapped",Toast.LENGTH_SHORT).show();
          return false;
        }
        return super.onOptionsItemSelected(item);
    }
}