package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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
            descriptionText.setText(model.getDescription());
            categoryText.setText(model.getCategory());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (model != null) {
            titleText.setText(model.getTitle());
            descriptionText.setText(model.getDescription());
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
            shareTask();
            return false;
        }
        if (id == R.id.edit) {
            Intent intent = new Intent(this,UpdateActivity.class);
            String title = titleText.getText().toString();
            String description = descriptionText.getText().toString();
            String category = categoryText.getText().toString();
            String taskId = model.getId();
            intent.putExtra("Title",title);
            intent.putExtra("Description",description);
            intent.putExtra("Category",category);
            intent.putExtra("Id", taskId);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void shareTask() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,titleText.getText().toString() + " - " + categoryText.getText().toString());
        shareIntent.putExtra(Intent.EXTRA_TEXT,descriptionText.getText().toString());
        shareIntent.setType("text/plain");
        shareIntent = Intent.createChooser(shareIntent,"Share via: ");
        startActivity(shareIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}