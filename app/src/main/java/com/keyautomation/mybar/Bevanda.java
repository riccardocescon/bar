package com.keyautomation.mybar;

public abstract class Bevanda {

    private static int auto_id = 1;

    private int id;
    private String nome;
    private float grado;
    private float prezzo;

    public Bevanda(String nome, float grado, float prezzo){
        //load auto_id
        id = auto_id;
        auto_id++;

        this.nome = nome;
        this.grado = grado;
        this.prezzo = prezzo;
    }

}
