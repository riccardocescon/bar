package com.keyautomation.mybar;

public abstract class Ordine_Bevanda {
    private int fk_ordine;
    private int fk_bevanda;

    public Ordine_Bevanda(int fk_ordine, int fk_bevanda){
        this.fk_ordine = fk_ordine;
        this.fk_bevanda = fk_bevanda;
    }
}
