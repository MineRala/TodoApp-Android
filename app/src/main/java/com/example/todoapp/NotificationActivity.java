package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity {
    public Button button;
    public static NotificationActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        instance = this;

        button = findViewById(R.id.addNotfication);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.GONE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                NotificationFragment myFragment = new NotificationFragment();
                transaction.replace(R.id.fragmentContainerNotification, myFragment, "NotificationFragment");
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            button.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }

    public static NotificationActivity getInstance() { return instance; }
}