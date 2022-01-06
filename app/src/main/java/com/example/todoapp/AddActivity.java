package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText titleText, descriptionText, categoryText;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleText = findViewById(R.id.editTextTitle);
        descriptionText = findViewById(R.id.editTextDesc);
        categoryText = findViewById(R.id.editTextCategory);
        addButton = findViewById(R.id.buttonAdd);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(titleText.getText().toString()) && !TextUtils.isEmpty(descriptionText.getText().toString()) && !TextUtils.isEmpty(categoryText.getText().toString())) {
                    Database database = new Database(AddActivity.this);
                    database.addTasks(titleText.getText().toString(),descriptionText.getText().toString(),categoryText.getText().toString());
                    Intent intent = new Intent(AddActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddActivity.this,getResources().getString(R.string.all_fields), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }

    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            descriptionText.setText(sharedText);
        }
    }
}