package com.keyautomation.mybar;

import android.database.Cursor;

public class Drink extends AutoId{

    private String name;
    private float alcohol;
    private float price;

    public Drink(String name, float alcohol, float price){
        //load auto_id
        id = auto_drink_id;
        auto_drink_id++;

        this.name = name;
        this.alcohol = alcohol;
        this.price = price;
    }

    public Drink(Cursor cursor){
        this.id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Table_ID));
        this.name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FLD_Drink_NAME));
        this.alcohol = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.FLD_Drink_ALCOHOL));
        this.price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.FLD_Drink_PRICE));
    }

    public String getName(){ return name; }
    public float getAlcohol(){ return alcohol; }
    public float getPrice(){ return price; }

}
