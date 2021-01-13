package com.keyautomation.mybar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView error_text;

    private LoadingFragment progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText nome = findViewById(R.id.activity_login_input_name_text);
        TextInputEditText password = findViewById(R.id.activity_login_input_password_text);
        error_text = findViewById(R.id.activity_login_error_text);
        Button login_button = findViewById(R.id.activity_login_button);

        progressDialog = LoadingFragment.getInstance("Loading", null);

        //controlla database
        Context c = this;
        String db_name = DatabaseHelper.getDbName();
        int version = DatabaseHelper.getDbVersion();

        DatabaseHelper db = new DatabaseHelper(c, db_name, version);

        //Waiter primo = new Waiter("tom", "psw", new Date(System.nanoTime()), new Date(System.nanoTime()), 100);
        //Waiter secondo = new Waiter("matt", "psw", new Date(System.nanoTime()), new Date(System.nanoTime()), 80);

        login_button.setOnClickListener(e ->{
            List<Waiter> waiters = DatabaseHelper.instance.getWaitersList("name = '" + nome.getText().toString() + "'");
            if(waiters.size() == 0){
                printError("User does not exists");
                return;
            }
            for(Waiter w : waiters){
                if(w.getPassword().equals(password.getText().toString())){
                    progressDialog.show(getSupportFragmentManager(), LoadingFragment.WAIT_DIALOG_TAG);
                    break;
                }else {
                    printError("Wrong password");
                }
            }
        });
    }

    private void printError(String message){
        error_text.setVisibility(View.VISIBLE);
        error_text.setText(message);
    }
}