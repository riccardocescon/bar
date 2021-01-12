package com.keyautomation.mybar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbbar";
    private static final int DB_VERSION = 1;

    //region Tabelle
        private static final String TB_Tavoli = "tb_tavoli";
        private static final String TB_Ordini = "tb_ordini";
        private static final String TB_Ordini_Bevande = "tb_ordini_bevande";
        private static final String TB_Ordini_Camerieri = "tb_ordini_camerieri";
        private static final String TB_Bevande = "tb_bevande";
        private static final String TB_Camerieri = "tb_camerieri";
    //endregion

    //region Campi
        //region Tavolo
            public static final String FLD_TAVOLO_ID = "id";
            public static final String FLD_TAVOLO_N_POSTI = "n_posti";
        //endregion

        //region Bevanda
            public static final String FLD_BEVANDA_ID = "id";
            public static final String FLD_BEVANDA_NOME = "nome";
            public static final String FLD_BEVANDA_GRADO = "grado";
            public static final String FLD_BEVANDA_PREZZO = "prezzo";
        //endregion

        //region Cameriere
            public static final String FLD_CAMERIERE_ID = "id";
            public static final String FLD_CAMERIERE_NOME = "nome";
            public static final String FLD_CAMERIERE_PASSWORD = "password";
            public static final String FLD_CAMERIERE_NASCITA = "nascita";
            public static final String FLD_CAMERIERE_INIZIO_CONTRATTO = "inizio_contratto";
            public static final String FLD_CAMERIERE_STIPENDIO = "stipendio";
        //endregion

        //region Ordini
            public static final String FLD_ORDINI_ID = "id";
            public static final String FLD_ORDINI_SERVITI = "serviti";
            public static final String FLD_ORDINI_PAGATI = "pagati";
        //endregion

        //region Ordine-Bevanda
            public static final String FLD_ORDINI___BEVANDE_FK_ORDINE = "fk_ordine";
            public static final String FLD_ORDINI___BEVANDE_FK_BEVANDA = "fk_bevanda";
        //endregion

        //region Ordine-Cameriere
            public static final String FLD_ORDINI___CAMERIERI_FK_ORDINE = "fk_ordine";
            public static final String FLD_ORDINI___CAMERIERI_FK_CAMERIERE = "fk_cameriere";
        //endregion

    //endregion

    //region Creazione Tabelle
        private static final String CREATE_TB_TAVOLI_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Tavoli + " (" +
                    FLD_TAVOLO_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_TAVOLO_N_POSTI + " VARCHAR(20)" +
                    ")";

        private static final String CREATE_TB_ORDINI_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Ordini + " (" +
                    FLD_ORDINI_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_ORDINI_SERVITI + " BOOLEAN ," +
                    FLD_ORDINI_PAGATI + " BOOLEAN" +
                    ")";

        //Aggiungi le altre tabelle

    //endregion





    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
