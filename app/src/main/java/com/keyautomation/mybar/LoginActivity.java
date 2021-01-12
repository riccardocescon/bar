package com.keyautomation.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText nome = findViewById(R.id.activity_login_input_name_text);
        TextInputEditText password = findViewById(R.id.activity_login_input_password_text);

        //controlla database

    }
}