package com.keyautomation.mybar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity{

    private ListViewAdapter mAdapter;

    private List<Order> orderList = new ArrayList<>();

    private ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);

        Button addTable = findViewById(R.id.view_list_Add_table);

        Button addDrink = findViewById(R.id.view_list_Add_drink);

        Button addOrder = findViewById(R.id.view_list_Add_order);

        addOrder.setOnClickListener(e ->{
            Intent myIntent = new Intent(this, RegisterOrderAction.class);
            startActivity(myIntent);
        });

        addTable.setOnClickListener(e ->{
            Intent myIntent = new Intent(this, RegisterTable.class);
            startActivity(myIntent);
        });

        addDrink.setOnClickListener(e ->{
            Intent myIntent = new Intent(this, RegisterDrinkActivity.class);
            startActivity(myIntent);
        });

        orderList = DatabaseHelper.instance.getOrdersList();

        listview = findViewById(R.id.view_list_element);
        listview.setAdapter(mAdapter = new ListViewAdapter(this, orderList));

        listview.setOnItemClickListener((adapterView, view, position, id) -> {
            Order order = orderList.get(position);
            Table table = DatabaseHelper.instance.getTableByOrder(DatabaseHelper.FLD_Orders___Tables_FK_Order + " = " + order.getID());
            Waiter waiter = DatabaseHelper.instance.getWaiterByOrder(DatabaseHelper.FLD_Orders___Waiters_FK_Order + " = " + order.getID());
            ArrayList<Drink> drinks = (ArrayList<Drink>) DatabaseHelper.instance.getDrinksByOrder(DatabaseHelper.FLD_Orders___Drinks_FK_Order + " = " + order.getID());


            Intent myIntent = new Intent(this,  OrderInfoActivity.class);
            myIntent.putExtra("table", table);
            myIntent.putExtra("waiter", waiter);
            myIntent.putParcelableArrayListExtra("drinks", drinks);
            myIntent.putExtra("order", order);
            startActivity(myIntent);
        });

    }

}