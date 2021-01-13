package com.keyautomation.mybar;

public abstract class Drink extends AutoId{

    private long id;
    private String name;
    private float alcohol;
    private float price;

    public Drink(String name, float alcohol, float price){
        //load auto_id
        id = auto_id;
        auto_id++;

        this.name = name;
        this.alcohol = alcohol;
        this.price = price;
    }

}
