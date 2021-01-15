package com.keyautomation.mybar;

import android.database.Cursor;

public class Orders_Waiters {
    private long fk_order;
    private long fk_waiter;

    public Orders_Waiters(long fk_order, long fk_waiter){
        this.fk_order = fk_order;
        this.fk_waiter = fk_waiter;
    }

    public Orders_Waiters(Cursor cursor){
        fk_order = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders___Waiters_FK_Order));
        fk_waiter = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders___Waiters_FK_Waiter));
    }

    public long getFkOrder(){ return fk_order; }
    public long getFkWaiter(){ return fk_waiter; }
}
