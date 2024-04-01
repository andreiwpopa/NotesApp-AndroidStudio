package com.example.proiectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.proiectandroid.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();

                if (email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(Signup.this,"All fields are required", Toast.LENGTH_LONG).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if(!checkUserEmail) {
                            Boolean insert = databaseHelper.insertData(email, password);

                            if (insert) {
                                Toast.makeText(Signup.this,"Signup Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Signup.this,"Signup Failed", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Signup.this,"User already exists", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Signup.this,"Invalid Password", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}