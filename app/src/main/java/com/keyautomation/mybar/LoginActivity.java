package com.keyautomation.mybar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class LoginActivity extends AppCompatActivity implements LoadingDialogFragment.DialogDismissListener {

    public static LoginActivity instance;

    TextView error_text;

    private LoadingDialogFragment progressDialog;

    private DatabaseHelper db;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(instance == null)instance = this;

        EditText nome = findViewById(R.id.activity_login_input_name_text);
        EditText password = findViewById(R.id.activity_login_input_password_text);
        error_text = findViewById(R.id.activity_login_error_text);
        Button login_button = findViewById(R.id.activity_login_button);

        progressDialog = LoadingDialogFragment.getInstance("Loading");


        loadDatabase();

        if(DatabaseHelper.getInstance(this).getWaitersList().size() == 0) {
            Waiter primo = new Waiter("tom", "psw", new Date(System.nanoTime()), new Date(System.nanoTime()), 100);
            Waiter secondo = new Waiter("matt", "psw", new Date(System.nanoTime()), new Date(System.nanoTime()), 80);

            Table t1 = new Table(4);
            Table t2 = new Table(3);
            Table t3 = new Table(3);
            Table t4 = new Table(3);
            Table t5 = new Table(3);
            Table t6 = new Table(3);
            Table t7 = new Table(3);
            Table t8 = new Table(3);

            Drink d1 = new Drink("Red", 10, 3);
            Drink d2 = new Drink("Dark", 8, 5);
            Drink d3 = new Drink("Leffe", 9, 4);


            DatabaseHelper.getInstance(this).addOrUpdateWaiter(primo);
            DatabaseHelper.getInstance(this).addOrUpdateWaiter(secondo);

            DatabaseHelper.getInstance(this).addOrUpdateTable(t1);
            DatabaseHelper.getInstance(this).addOrUpdateTable(t2);
            DatabaseHelper.getInstance(this).addOrUpdateTable(t3);
            DatabaseHelper.getInstance(this).addOrUpdateTable(t4);
            DatabaseHelper.getInstance(this).addOrUpdateTable(t5);
            DatabaseHelper.getInstance(this).addOrUpdateTable(t6);
            DatabaseHelper.getInstance(this).addOrUpdateTable(t7);
            DatabaseHelper.getInstance(this).addOrUpdateTable(t8);

            DatabaseHelper.getInstance(this).addOrUpdateDrink(d1);
            DatabaseHelper.getInstance(this).addOrUpdateDrink(d2);
            DatabaseHelper.getInstance(this).addOrUpdateDrink(d3);
        }



        /*Order o1 = new Order(1,0);
        o1.addDrink(d1);
        o1.addDrink(d2);

        Order o2 = new Order(1,0);
        o2.addDrink(d3);
        o2.addDrink(d2);
        o2.addDrink(d1);

        Orders_Tables ot1 = new Orders_Tables(o1.getID(), t1.getID());
        Orders_Tables ot2 = new Orders_Tables(o2.getID(), t2.getID());

        Orders_Drinks od1 = new Orders_Drinks(o1.getID(), d1.getID());
        Orders_Drinks od2 = new Orders_Drinks(o1.getID(), d2.getID());
        Orders_Drinks od3 = new Orders_Drinks(o2.getID(), d3.getID());
        Orders_Drinks od4 = new Orders_Drinks(o2.getID(), d2.getID());
        Orders_Drinks od5 = new Orders_Drinks(o2.getID(), d1.getID());

        Orders_Waiters ow1 = new Orders_Waiters(o1.getID(), primo.getID());
        Orders_Waiters ow2 = new Orders_Waiters(o2.getID(), secondo.getID());*/

       /* DatabaseHelper.instance.addOrUpdateOrder(o1);
        DatabaseHelper.instance.addOrUpdateOrder(o2);

        DatabaseHelper.instance.addOrUpdateOrderTable(ot1);
        DatabaseHelper.instance.addOrUpdateOrderTable(ot2);

        DatabaseHelper.instance.addOrUpdateOrderDrinks(od1);
        DatabaseHelper.instance.addOrUpdateOrderDrinks(od2);
        DatabaseHelper.instance.addOrUpdateOrderDrinks(od3);
        DatabaseHelper.instance.addOrUpdateOrderDrinks(od4);
        DatabaseHelper.instance.addOrUpdateOrderDrinks(od5);

        DatabaseHelper.instance.addOrUpdateOrderWaiters(ow1);
        DatabaseHelper.instance.addOrUpdateOrderWaiters(ow2);*/


        login_button.setOnClickListener(e ->{
            List<Waiter> all = DatabaseHelper.getInstance(this).getWaitersList();
            System.out.println("List : " + all.size());
            all.forEach(wr ->{
                System.out.println("Name : " + wr.getName());
                System.out.println("pass : " + wr.getPassword());
            });
            String request = DatabaseUtils.sqlEscapeString(nome.getText().toString());
            List<Waiter> waiters = DatabaseHelper.getInstance(this).getWaitersList("name = " + request);
            System.out.println("Size : " + waiters.size());
            if(waiters.size() == 0){
                printError("User does not exists");
                return;
            }
            for(Waiter w : waiters){
                if(w.getPassword().equals(password.getText().toString())){
                    progressDialog.show(getSupportFragmentManager(), LoadingDialogFragment.WAIT_DIALOG_TAG);
                    break;
                }else {
                    printError("Wrong password");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void loadDatabase(){
        Context c = this;
        String db_name = DatabaseHelper.getDbName();
        int version = DatabaseHelper.getDbVersion();

        db = new DatabaseHelper(c, db_name, version);
    }

    private void printError(String message){
        error_text.setVisibility(View.VISIBLE);
        error_text.setText(message);
    }

    public void loadOrdersActivity(){
        Intent myIntent = new Intent(this, OrdersActivity.class);
        db.close();
        Log.d("Loading : ", String.valueOf(myIntent));
        startActivity(myIntent);
    }

    @Override
    public void onDialogDismiss() {

    }
}