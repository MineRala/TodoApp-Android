package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText inputEmail;
    private Button sendTheResetLink;
    private TextView backToLogin;
    private FirebaseAuth auth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        inputEmail = findViewById(R.id.inputEmail);
        sendTheResetLink = findViewById(R.id.buttonSendResetLink);
        backToLogin = findViewById(R.id.backToLoginTextView);
        auth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(ForgotPassword.this);

        sendTheResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
            }
        });

    }

    private void checkCredentials() {
        String email = inputEmail.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(inputEmail, getResources().getString(R.string.email_not_valid));
        }
        else {
            loadingBar.setTitle(getResources().getString(R.string.reset_password));
            loadingBar.setMessage(getResources().getString(R.string.check_credentials));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(ForgotPassword.this,getResources().getString(R.string.check_email),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPassword.this,getResources().getString(R.string.try_again),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void showError(EditText text, String s) {
        text.setError(s);
        text.requestFocus();
    }
}