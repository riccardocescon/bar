package com.keyautomation.mybar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText nome = findViewById(R.id.activity_login_input_name_text);
        TextInputEditText password = findViewById(R.id.activity_login_input_password_text);

        //controlla database
        Context c = this;
        String db_name = DatabaseHelper.getDbName();
        int version = DatabaseHelper.getDbVersion();

        DatabaseHelper db = new DatabaseHelper(c, db_name, version);

        //Waiter primo = new Waiter("tom", "psw", new Date(System.nanoTime()), new Date(System.nanoTime()), 100);
        //Waiter secondo = new Waiter("matt", "psw", new Date(System.nanoTime()), new Date(System.nanoTime()), 80);

        List<Waiter> waiters = DatabaseHelper.instance.getWaitersList();
        System.out.println("SIZE : " + waiters.size());
        waiters.forEach(t ->{
            System.out.println("Table id : " + t.getID() + " name : " + t.getName());//stampa solo il secondo (?)
        });

    }
}