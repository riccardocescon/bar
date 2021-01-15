package com.keyautomation.mybar;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Order extends AutoId{

    private int served;
    private int paid;

    //vars not inside database
    private List<Drink> drinks = new ArrayList<>();
    private long table_id;

    public Order(int served, int paid){
        id = auto_order_id;
        auto_order_id++;

        this.served = served;
        this.paid = paid;
    }

    public Order(Cursor cursor){
        try{
            id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_ID));
            served = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_SERVED));
            paid = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_PAID));
            drinks = DatabaseHelper.instance.getDrinksByOrder("fk_order = " + id);
            table_id = DatabaseHelper.instance.getTableByOrder( "fk_order = " + id).getID();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void assignRemainingVars(){
        drinks = DatabaseHelper.instance.getDrinksByOrder("fk_order = " + id);
        table_id = DatabaseHelper.instance.getTableByOrder( "fk_order = " + id).getID();
    }

    public int getServed(){ return served; }
    public int getPaid(){ return paid; }
    public void addDrink(Drink drink){ drinks.add(drink); }
    public List<Drink> getDrinks(){ return drinks; }
    public long getTableId(){ return table_id; }

}
