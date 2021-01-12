package com.keyautomation.mybar;

public abstract class Ordine_Cameriere {
    private int fk_ordine;
    private int fk_cameriere;

    public Ordine_Cameriere(int fk_ordine, int fk_cameriere){
        this.fk_ordine = fk_ordine;
        this.fk_cameriere = fk_cameriere;
    }
}
