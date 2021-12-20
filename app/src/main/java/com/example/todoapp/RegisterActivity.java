package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    private TextView alreadyHaveAnAccountButton;
    private Button registerButton;
    private FirebaseAuth auth;
    private ProgressDialog loadingBar;
    private static final String SHARED_PREF_NAME = "username";
    private static final String KEY_NAME = "key_username";
    String username,email,password,passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        alreadyHaveAnAccountButton = findViewById(R.id.textViewAlreadyHaveAccount);
        registerButton = findViewById(R.id.buttonRegister);
        auth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(RegisterActivity.this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
                saveName();
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
         username = inputUsername.getText().toString();
         email = inputEmail.getText().toString();
         password = inputPassword.getText().toString();
         passwordConfirm = inputConfirmPassword.getText().toString();

        if (username.isEmpty() || username.length() < 8) {
            showError(inputUsername, getResources().getString(R.string.username_not_valid));
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, getResources().getString(R.string.email_not_valid));
        } else if (password.isEmpty() || password.length() < 8) {
            showError(inputPassword, getResources().getString(R.string.password_not_valid));
        } else if (passwordConfirm.isEmpty() || !passwordConfirm.equals(password)) {
            showError(inputConfirmPassword,getResources().getString(R.string.password_not_match));
        }
        else {
            loadingBar.setTitle(getResources().getString(R.string.registration));
            loadingBar.setMessage(getResources().getString(R.string.check_credentials));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();

                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(RegisterActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(EditText text, String s) {
        text.setError(s);
        text.requestFocus();
    }

    private void saveName() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME,inputUsername.getText().toString());
        editor.apply();
    }


}