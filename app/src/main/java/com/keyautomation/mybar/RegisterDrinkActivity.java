package com.keyautomation.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterDrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_drink);

        EditText name = findViewById(R.id.activity_register_drink_name);
        EditText alcohol = findViewById(R.id.activity_register_drink_alcohol);
        EditText price = findViewById(R.id.activity_register_drink_price);

        Button save_button = findViewById(R.id.activity_register_drink_button);
        ImageView back_button = findViewById(R.id.activity_register_drink_back);

        back_button.setOnClickListener(e ->{
            Intent myIntent = new Intent(this, OrdersActivity.class);
            startActivity(myIntent);
        });

        save_button.setOnClickListener(e -> {

            if(name.getText().equals("") || alcohol.getText().equals("") || price.getText().equals(""))return;

            float alchol_value = Float.parseFloat(String.valueOf(alcohol.getText()));
            float price_value = Float.parseFloat(String.valueOf(price.getText()));

            Drink drink = new Drink(String.valueOf(name.getText()), alchol_value, price_value);

            DatabaseHelper.instance.addOrUpdateDrink(drink);

            name.setText("");
            alcohol.setText("");
            price.setText("");
        });

    }
}