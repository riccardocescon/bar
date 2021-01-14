package com.keyautomation.mybar;

import android.database.Cursor;

public class Orders_Drinks {
    private long fk_order;
    private long fk_drink;

    public Orders_Drinks(long fk_order, long fk_drink){
        this.fk_order = fk_order;
        this.fk_drink = fk_drink;
    }

    public Orders_Drinks(Cursor cursor) {
        try{
            fk_order = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders___Drinks_FK_Order));
            fk_drink = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders___Drinks_FK_Drink));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public long getFkOrder(){ return fk_order; }
    public long getFkDrink(){ return fk_drink; }
}
