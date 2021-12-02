package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    private TextView alreadyHaveAnAccountButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        alreadyHaveAnAccountButton = findViewById(R.id.textViewalreadyHaveAccount);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });
        alreadyHaveAnAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordConfirm = inputConfirmPassword.getText().toString();

        if (username.isEmpty() || username.length() < 8) {
            showError(inputUsername, "Your username is not valid! Username must be 8 character.");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email is not valid! Email must contains '@'.");
        } else if (password.isEmpty() || password.length() < 8) {
            showError(inputPassword, "Password is not valid! Password must be 8 character.");
        } else if (passwordConfirm.isEmpty() || !passwordConfirm.equals(password)) {
            showError(inputConfirmPassword,"Password not match!");
        }
        else {
            Toast.makeText(this,"Call Registration Method!",Toast.LENGTH_SHORT).show();
        }
    }

    private void showError(EditText text, String s) {
        text.setError(s);
        text.requestFocus();
    }
}