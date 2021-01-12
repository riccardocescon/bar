package com.keyautomation.mybar;

public abstract class Tavolo {

    private static int auto_id = 1;

    private int id;
    private int n_posti;

    public Tavolo(int n_posti){
        //load auto_id
        id = auto_id;
        auto_id++;

        this.n_posti = n_posti;
    }

    public void setPosti(int n_posti){this.n_posti = n_posti;}
    public int getPosti(){return n_posti;}

}
