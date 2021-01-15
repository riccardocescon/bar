package com.keyautomation.mybar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_order_action);

        context = this;

        mAdapter = new DrinkOrderAdapter(context, picked_drinks);

        Button save_button = findViewById(R.id.activity_register_order_save_button);

        Spinner table_sipnner = (Spinner) findViewById(R.id.activity_register_order_table_spinner);
        Spinner drink_sipnner = (Spinner) findViewById(R.id.activity_register_order_drink_spinner);
        Spinner waiter_sipnner = (Spinner) findViewById(R.id.activity_register_order_waiter_spinner);

        List<Table> table_list = DatabaseHelper.instance.getTablesList();
        List<Drink> drink_list = DatabaseHelper.instance.getDrinksList();
        List<Waiter> waiter_list = DatabaseHelper.instance.getWaitersList();

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
            int table_id = Integer.parseInt(table_text.substring(6, table_text.length() - 1));
            Table table = DatabaseHelper.instance.getTablesList(DatabaseHelper.FLD_Table_ID + " = " + table_id).get(0);

            Order order = new Order(1,0);

            Orders_Tables orders_tables = new Orders_Tables(order.getID(), table.getID());
            DatabaseHelper.instance.addOrUpdateOrderTable(orders_tables);

            String waiter_name = waiter_sipnner.getSelectedItem().toString();
            Waiter waiter = DatabaseHelper.instance.getWaitersList(DatabaseHelper.FLD_Waiter_NAME + " = " + waiter_name).get(0);

            Orders_Waiters orders_waiters = new Orders_Waiters(order.getID(), waiter.getID());
            DatabaseHelper.instance.addOrUpdateOrderWaiters(orders_waiters);

            List<Orders_Drinks> orders_drinks = new ArrayList<>();
            for(Drink drink : picked_drinks){
                Orders_Drinks od = new Orders_Drinks(order.getID(), drink.getID());
                orders_drinks.add(od);
            }
            DatabaseHelper.instance.addOrUpdateOrderWaiters(orders_waiters);

        });

        setSpinners(table_list, drink_list, waiter_list, table_sipnner, drink_sipnner, waiter_sipnner);

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