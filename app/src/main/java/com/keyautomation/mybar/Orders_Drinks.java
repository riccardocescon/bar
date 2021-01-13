package com.keyautomation.mybar;

public abstract class Orders_Drinks {
    private int fk_order;
    private int fk_drink;

    public Orders_Drinks(int fk_order, int fk_drink){
        this.fk_order = fk_order;
        this.fk_drink = fk_drink;
    }
}
