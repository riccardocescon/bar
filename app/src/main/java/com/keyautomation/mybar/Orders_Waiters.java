package com.keyautomation.mybar;

public abstract class Orders_Waiters {
    private int fk_order;
    private int fk_waiter;

    public Orders_Waiters(int fk_order, int fk_waiter){
        this.fk_order = fk_order;
        this.fk_waiter = fk_waiter;
    }
}
