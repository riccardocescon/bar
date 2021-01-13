package com.keyautomation.mybar;

public abstract class Order extends AutoId{

    private long id;
    private boolean served;
    private boolean paid;

    public Order(boolean served, boolean paid){
        //load auto_id
        id = auto_id;
        auto_id++;

        this.served = served;
        this.paid = paid;
    }

}
