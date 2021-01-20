package com.keyautomation.mybar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

public class OrdersActivity extends AppCompatActivity {

    private ListViewAdapter mAdapter;

    private List<Order> orderList = new ArrayList<>();

    private ListView listview;

    private DatabaseHelper db;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);

        loadDatabase();

        Button addTable = findViewById(R.id.view_list_Add_table);

        Button addDrink = findViewById(R.id.view_list_Add_drink);

        Button addOrder = findViewById(R.id.view_list_Add_order);

        Button served_button = findViewById(R.id.order_item_button);

        Button paid_button = findViewById(R.id.order_item_paid);

        addOrder.setOnClickListener(e -> {
            Intent myIntent = new Intent(this, RegisterOrderAction.class);
            startActivity(myIntent);
        });

        addTable.setOnClickListener(e -> {
            Intent myIntent = new Intent(this, RegisterTable.class);
            startActivity(myIntent);
        });

        addDrink.setOnClickListener(e -> {
            Intent myIntent = new Intent(this, RegisterDrinkActivity.class);
            startActivity(myIntent);
        });

        showServed();

        served_button.setOnClickListener(e -> showServed());

        paid_button.setOnClickListener(e -> showPaid());

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void loadDatabase() {
        Context c = this;
        String db_name = DatabaseHelper.getDbName();
        int version = DatabaseHelper.getDbVersion();

        if (db == null)
            db = new DatabaseHelper(c, db_name, version);
    }

    private void showServed() {
        orderList = DatabaseHelper.getInstance(this).getOrdersList(DatabaseHelper.FLD_Orders_PAID + " = " + 0);

        listview = findViewById(R.id.view_list_element);
        listview.setAdapter(mAdapter = new ListViewAdapter(this, orderList));

        listview.setOnItemClickListener((adapterView, view, position, id) -> {
            Order order = orderList.get(position);
            Table table = DatabaseHelper.getInstance(this).getTableByOrder(DatabaseHelper.FLD_Orders___Tables_FK_Order + " = " + order.getID());
            Waiter waiter = DatabaseHelper.getInstance(this).getWaiterByOrder(DatabaseHelper.FLD_Orders___Waiters_FK_Order + " = " + order.getID());
            ArrayList<Drink> drinks = (ArrayList<Drink>) DatabaseHelper.getInstance(this).getDrinksByOrder(DatabaseHelper.FLD_Orders___Drinks_FK_Order + " = " + order.getID());

            Intent myIntent = new Intent(this, OrderInfoActivity.class);
            myIntent.putExtra("table", table);
            myIntent.putExtra("waiter", waiter);
            myIntent.putParcelableArrayListExtra("drinks", drinks);
            myIntent.putExtra("order", order);
            startActivity(myIntent);
        });
    }

    private void showPaid() {
        orderList = DatabaseHelper.getInstance(this).getOrdersList(DatabaseHelper.FLD_Orders_PAID + " = " + 1);

        listview = findViewById(R.id.view_list_element);
        listview.setAdapter(mAdapter = new ListViewAdapter(this, orderList));

        listview.setOnItemClickListener((adapterView, view, position, id) -> {
            Order order = orderList.get(position);
            Table table = DatabaseHelper.getInstance(this).getTableByOrder(DatabaseHelper.FLD_Orders___Tables_FK_Order + " = " + order.getID());
            Waiter waiter = DatabaseHelper.getInstance(this).getWaiterByOrder(DatabaseHelper.FLD_Orders___Waiters_FK_Order + " = " + order.getID());
            ArrayList<Drink> drinks = (ArrayList<Drink>) DatabaseHelper.getInstance(this).getDrinksByOrder(DatabaseHelper.FLD_Orders___Drinks_FK_Order + " = " + order.getID());


            Intent myIntent = new Intent(this, OrderInfoActivity.class);
            myIntent.putExtra("table", table);
            myIntent.putExtra("waiter", waiter);
            myIntent.putParcelableArrayListExtra("drinks", drinks);
            myIntent.putExtra("order", order);
            db.close();
            startActivity(myIntent);
        });
    }

}