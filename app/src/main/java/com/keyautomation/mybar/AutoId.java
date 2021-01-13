package com.keyautomation.mybar;

public class AutoId {
    protected long id;
    protected static long auto_table_id = 1;
    protected static long auto_waiter_id = 1;
    protected static long auto_drink_id = 1;
    protected static long auto_order_id = 1;

    public static void setAutoTableId(long id){
        auto_table_id = id;
    }

    public static void setAutoWaiterId(long id){
        auto_waiter_id = id;
    }

    public static void setAutoDrinkId(long id){
        auto_drink_id = id;
    }

    public static void setAutoOrderId(long id){
        auto_order_id = id;
    }

    public long getID(){return id;}
}
