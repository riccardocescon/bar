package com.keyautomation.mybar;

public abstract class Drink extends AutoId{

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

}
