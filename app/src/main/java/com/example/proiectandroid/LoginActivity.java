package com.example.proiectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.proiectandroid.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
//        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
//
//        // Check if the user is already logged in
//        if (sharedPreferences.getBoolean("loggedIn", false)) {
//            // If already logged in, start the MainActivity directly
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                if (email.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                else {
                    int userId = databaseHelper.getUserId(email, password);
                    if (userId != -1) {
                        // Save login status and user ID to SharedPreferences
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean("loggedIn", true);
//                        editor.putInt("userId", userId);
//                        editor.apply();

                        Log.d("LoginActivity", "User ID: " + userId);


                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userId", userId); // Pass userId to MainActivity
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}