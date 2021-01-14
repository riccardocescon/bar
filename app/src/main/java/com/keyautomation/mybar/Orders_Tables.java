package com.keyautomation.mybar;

import android.database.Cursor;

public class Orders_Tables{

    private long fk_order;
    private long fk_table;

    public Orders_Tables(long fk_order, long fk_table){
        this.fk_order = fk_order;
        this.fk_table = fk_table;
    }

    public Orders_Tables(Cursor cursor) {
        fk_order = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders___Tables_FK_Order));
        fk_table = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders___Tables_FK_Table));
    }

    public long getFkOrder(){ return fk_order; }
    public long getFkTable(){ return fk_table; }
}
