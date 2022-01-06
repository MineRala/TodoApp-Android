package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText titleText, descriptionText, categoryText;
    Button updateButton;
    String title, description, category, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back);

        setSupportActionBar(toolbar);
        setTitle(R.string.update_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();

                if (extras != null) {
                    Intent intent = new Intent(UpdateActivity.this, DetailActivity.class);
                    String title = extras.getString("Title");
                    String description = extras.getString("Description");
                    String category = extras.getString("Category");
                    intent.putExtra("detail", new Model(id,title,description,category));
                    startActivity(intent);
                    finish();
                }
            }
        });

        titleText = findViewById(R.id.editTextTitle);
        descriptionText = findViewById(R.id.editTextDesc);
        categoryText = findViewById(R.id.editTextCategory);
        updateButton = findViewById(R.id.buttonUpdate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             title = extras.getString("Title");
             description = extras.getString("Description");
             category = extras.getString("Category");
             id = extras.getString("Id");
        }

        titleText.setText(title);
        descriptionText.setText(description);
        categoryText.setText(category);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(titleText.getText().toString()) && !TextUtils.isEmpty(descriptionText.getText().toString()) && !TextUtils.isEmpty(categoryText.getText().toString())) {
                    Database database = new Database(UpdateActivity.this);
                    database.updateTasks(titleText.getText().toString(),descriptionText.getText().toString(),categoryText.getText().toString(), id);
                    Intent intent = new Intent(UpdateActivity.this, DetailActivity.class);
                    String title = titleText.getText().toString();
                    String description = descriptionText.getText().toString();
                    String category = categoryText.getText().toString();
                    intent.putExtra("detail", new Model(id,title,description,category));
                    startActivity(intent);
                    finish();
                } else{
                    Toast.makeText(UpdateActivity.this,getResources().getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Intent intent = new Intent(UpdateActivity.this, DetailActivity.class);
            String title = extras.getString("Title");
            String description = extras.getString("Description");
            String category = extras.getString("Category");
            intent.putExtra("detail", new Model(id,title,description,category));
            startActivity(intent);
            finish();
        }
    }
}