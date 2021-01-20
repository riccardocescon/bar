package com.keyautomation.mybar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoActivity extends Activity {

    private ItemInfoAdapter mAdapter;

    private ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);


        Table table = getIntent().getExtras().getParcelable("table");
        Waiter waiter = getIntent().getExtras().getParcelable("waiter");
        Order order = getIntent().getExtras().getParcelable("order");
        Log.d("Waiter name", waiter.getName());
        List<Drink> drinks = new ArrayList<>();//getIntent().getParcelableArrayListExtra("drinks");
        drinks = compactList(order.getDrinks());

        Button pay_button = findViewById(R.id.activity_order_info_button);


        listview = findViewById(R.id.view_list_element);
        listview.setAdapter(mAdapter = new ItemInfoAdapter(this, drinks, order.getID()));


        TextView table_id = findViewById(R.id.activity_order_info_id);
        TextView served_text = findViewById(R.id.activity_order_info_served);
        table_id.setText(String.valueOf(table.getID()));
        ImageView back_button = findViewById(R.id.activity_order_info_back);


        back_button.setOnClickListener(e ->{
            Intent myIntent = new Intent(this, OrdersActivity.class);
            startActivity(myIntent);
        });

        served_text.setText(order.getServed() == 1 ? "Served by " + waiter.getName() : "Not served yet");

        pay_button.setOnClickListener(e ->{

            order.setPaid(1);
            DatabaseHelper.getInstance(this).addOrUpdateOrder(order);
            Intent myIntent = new Intent(this, OrdersActivity.class);
            startActivity(myIntent);

        });

    }

    private List<Drink> compactList(List<Drink> list){
        List<Drink> unique_drinks = new ArrayList<>();
        for(Drink d : list){
            if(!hasDrink(unique_drinks, d)){
                unique_drinks.add(d);
            }
        }
        return unique_drinks;
    }

    private boolean hasDrink(List<Drink> to_check, Drink drink){
        for(Drink d : to_check){
            if(d.getName().equals(drink.getName()))return true;
        }
        return false;
    }
}
