package com.keyautomation.mybar;

public abstract class Order extends AutoId{

    private boolean served;
    private boolean paid;

    public Order(boolean served, boolean paid){
        id = auto_order_id;
        auto_order_id++;

        this.served = served;
        this.paid = paid;
    }

}
