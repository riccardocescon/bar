package com.keyautomation.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_table);

        EditText value = findViewById(R.id.activity_register_table_value);
        Button save_button = findViewById(R.id.activity_register_table_button);
        ImageView back_button = findViewById(R.id.activity_register_table_back);

        save_button.setOnClickListener(e ->{
            if(value.getText() != null)return;
            int num_chairs = Integer.parseInt(String.valueOf(value.getText()));
            if(num_chairs > 0){
                Table table = new Table(num_chairs);
                DatabaseHelper.instance.addOrUpdateTable(table);
                value.setText("");
            }else{
                //print error
            }
        });

        back_button.setOnClickListener(e ->{
            Intent myIntent = new Intent(this, OrdersActivity.class);
            startActivity(myIntent);
        });

    }
}