package com.keyautomation.mybar;

public abstract class Ordine {

    private static int auto_id = 1;

    private int id;
    private boolean serviti;
    private boolean pagati;

    public Ordine(boolean serviti, boolean pagati){
        //load auto_id
        id = auto_id;
        auto_id++;

        this.serviti = serviti;
        this.pagati = pagati;
    }

}
