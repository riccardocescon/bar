package com.keyautomation.mybar;

import java.util.Date;

public abstract class Cameriere {

    private static int auto_id = 1;

    private int id;
    private String nome, password;
    private Date nascita, inizio_contratto;
    private float stipendio;

    public Cameriere(String nome, String password, Date nascita, Date inizio_contratto, float stipendio){
        //load auto_id
        id = auto_id;
        auto_id++;

        this.nome = nome;
        this.password = password;
        this.nascita = nascita;
        this.inizio_contratto = inizio_contratto;
        this.stipendio = stipendio;
    }

}
