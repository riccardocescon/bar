package com.keyautomation.mybar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RegisterOrderAction extends AppCompatActivity implements DrinkOrderAdapter.IDeleteButton {

    private DrinkOrderAdapter mAdapter;
    private List<Drink> picked_drinks = new ArrayList<>();
    private Context context;
    private DatabaseHelper db;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_order_action);

        loadDatabase();

        context = this;

        mAdapter = new DrinkOrderAdapter(context, picked_drinks);

        Button save_button = findViewById(R.id.activity_register_order_save_button);


        Spinner table_sipnner = findViewById(R.id.activity_register_order_table_spinner);
        Spinner drink_sipnner = findViewById(R.id.activity_register_order_drink_spinner);
        Spinner waiter_sipnner = findViewById(R.id.activity_register_order_waiter_spinner);

        List<Table> table_list = DatabaseHelper.getInstance(this).getTablesList();
        List<Drink> drink_list = DatabaseHelper.getInstance(this).getDrinksList();
        List<Waiter> waiter_list = DatabaseHelper.getInstance(this).getWaitersList();

        ListView drink_list_view = findViewById(R.id.activity_register_list_view);
        drink_list_view.setAdapter(mAdapter);

        drink_sipnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Drink drink = drink_list.get(position);
                int possibleIndex = picked_drinks.indexOf(drink);
                if (possibleIndex >= 0) {
                    picked_drinks.get(possibleIndex).increaseQuantity();
                } else {
                    picked_drinks.add(drink);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save_button.setOnClickListener(e ->{
            String table_text = table_sipnner.getSelectedItem().toString();

            int table_id = Integer.parseInt(table_text.substring(6));

            Table table = DatabaseHelper.getInstance(this).getTablesList(DatabaseHelper.FLD_Table_ID + " = " + table_id).get(0);

            Order order = new Order(this, 1,0);
            order.setID(DatabaseHelper.getInstance(this).addOrUpdateOrder(order));//table id not setted

            Orders_Tables orders_tables = new Orders_Tables(order.getID(), table.getID());
            DatabaseHelper.getInstance(this).addOrUpdateOrderTable(orders_tables);

            String waiter_name = DatabaseUtils.sqlEscapeString(waiter_sipnner.getSelectedItem().toString());
            Waiter waiter = DatabaseHelper.getInstance(this).getWaitersList(DatabaseHelper.FLD_Waiter_NAME + " = " + waiter_name).get(0);

            Orders_Waiters orders_waiters = new Orders_Waiters(order.getID(), waiter.getID());
            DatabaseHelper.getInstance(this).addOrUpdateOrderWaiters(orders_waiters);

            for(Drink drink : picked_drinks){
                for(int i = 0; i < drink.getQuantity(); i++) {
                    Orders_Drinks od = new Orders_Drinks(order.getID(), drink.getID());
                    DatabaseHelper.getInstance(this).addOrUpdateOrderDrinks(od);
                }
            }

            System.out.println("table : " + table.getID());
            System.out.println("Order : " + order.getID());
            System.out.println("orders_tables : " + orders_tables.getFkTable() );
            System.out.println("Waiter : " + waiter.getName());
            System.out.println("drink amont : " + picked_drinks.size());

            db.close();
            Intent myIntent = new Intent(this, OrdersActivity.class);
            startActivity(myIntent);

        });

        setSpinners(table_list, drink_list, waiter_list, table_sipnner, drink_sipnner, waiter_sipnner);

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void loadDatabase(){
        Context c = this;
        String db_name = DatabaseHelper.getDbName();
        int version = DatabaseHelper.getDbVersion();

        db = new DatabaseHelper(c, db_name, version);
    }

    private void setSpinners(List<Table> table_list, List<Drink> drink_list, List<Waiter> waiter_list, Spinner table_sipnner, Spinner drink_sipnner, Spinner waiter_sipnner){


        String[] table_strings = new String[table_list.size()];

        for(int i = 0; i < table_strings.length; i++){
            table_strings[i] = "Table " + table_list.get(i).getID();
        }

        String[] drink_strings = new String[drink_list.size()];

        for(int i = 0; i < drink_strings.length; i++){
            drink_strings[i] = drink_list.get(i).getName();
        }

        String[] waiter_strings = new String[waiter_list.size()];

        for(int i = 0; i < waiter_strings.length; i++){
            waiter_strings[i] = waiter_list.get(i).getName();
        }

        ArrayAdapter<String> table_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, table_strings);
        table_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        table_sipnner.setAdapter(table_adapter);

        ArrayAdapter<String> drink_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drink_strings);
        drink_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drink_sipnner.setAdapter(drink_adapter);

        ArrayAdapter<String> waiter_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, waiter_strings);
        waiter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waiter_sipnner.setAdapter(waiter_adapter);

        //picked_drinks.get(2).getQuantity()

    }

    @Override
    public void OnButtonDelete(int position) {
        picked_drinks.remove(position);
        mAdapter.notifyDataSetChanged();
    }
}