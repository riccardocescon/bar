package com.keyautomation.mybar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static DatabaseHelper instance;

    private static final String DB_NAME = "dbbar";
    private static final int DB_VERSION = 1;

    //region Tabelle
        private static final String TB_Tables = "tb_tables";
        private static final String TB_Orders = "tb_orders";
        private static final String TB_Orders_Drinks = "tb_orders_drinks";
        private static final String TB_Orders_Waiters = "tb_orders_waiters";
        private static final String TB_Drinks = "tb_drinks";
        private static final String TB_Waiters = "tb_waiters";
    //endregion

    //region Campi
        //region Table
            public static final String FLD_Table_ID = "id";
            public static final String FLD_Table_N_POSTI = "n_posti";
        //endregion

        //region Drink
            public static final String FLD_Drink_ID = "id";
            public static final String FLD_Drink_NOME = "nome";
            public static final String FLD_Drink_GRADO = "grado";
            public static final String FLD_Drink_PREZZO = "prezzo";
        //endregion

        //region Waiter
            public static final String FLD_Waiter_ID = "id";
            public static final String FLD_Waiter_NOME = "nome";
            public static final String FLD_Waiter_PASSWORD = "password";
            public static final String FLD_Waiter_NASCITA = "nascita";
            public static final String FLD_Waiter_INIZIO_CONTRATTO = "inizio_contratto";
            public static final String FLD_Waiter_STIPENDIO = "stipendio";
        //endregion

        //region Orders
            public static final String FLD_Orders_ID = "id";
            public static final String FLD_Orders_SERVITI = "serviti";
            public static final String FLD_Orders_PAGATI = "pagati";
        //endregion

        //region Order-Drink
            public static final String FLD_Orders___Drinks_FK_Order = "fk_order";
            public static final String FLD_Orders___Drinks_FK_Drink = "fk_drink";
        //endregion

        //region Order-Waiter
            public static final String FLD_Orders___Waiters_FK_Order = "fk_order";
            public static final String FLD_Orders___Waiters_FK_Waiter = "fk_waiter";
        //endregion

    //endregion

    //region Creazione Tabelle

        private static final String CREATE_TB_Tables_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Tables + " (" +
                    FLD_Table_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_Table_N_POSTI + " VARCHAR(20)" +
                    ")";

        private static final String CREATE_TB_Orders_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders + " (" +
                    FLD_Orders_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_Orders_SERVITI + " BOOLEAN ," +
                    FLD_Orders_PAGATI + " BOOLEAN" +
                    ")";

        private static final String CREATE_TB_Drinks_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Drinks + " (" +
                    FLD_Drink_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_Drink_NOME + " FLOAT ," +
                    FLD_Drink_GRADO + " FLOAT ," +
                    FLD_Drink_PREZZO + "FLOAT "+
                    ")";

        private static final String CREATE_TB_Waiters_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Waiters + " (" +
                    FLD_Orders_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_Waiter_NOME + " VARCHAR(20) ," +
                    FLD_Waiter_PASSWORD + " VARCHAR(100)," +
                    FLD_Waiter_NASCITA + "DATE," +
                    FLD_Waiter_INIZIO_CONTRATTO + "DATE," +
                    FLD_Waiter_STIPENDIO + "FLOAT" +
                    ")";

        private static final String CREATE_TB_Orders___Drinks_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders_Drinks + " (" +
                    FLD_Orders___Drinks_FK_Order + " INTEGER, " +
                    FLD_Orders___Drinks_FK_Drink + " INTEGER " +
                    ")";

        private static final String CREATE_TB_Orders___Waiters_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders_Waiters + " (" +
                    FLD_Orders___Waiters_FK_Order + " INTEGER, " +
                    FLD_Orders___Waiters_FK_Waiter + " INTEGER " +
                    ")";

    //endregion

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version){
        super(context, name, null, version);
        if(instance == null)instance = this;

        loadAutoId();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        updateDatabaseVersion(db, oldVersion, newVersion);

    }

    //region Select Queries

        public synchronized List<Table> getTablesList(String... whereClause) {
            SQLiteDatabase db = getReadableDatabase();
            List<Table> TablesList = new ArrayList<>();
            Cursor cursor;
            StringBuilder sqlQuery = new StringBuilder("SELECT * FROM " + TB_Tables);
            if (whereClause != null && whereClause.length > 0) {
                boolean concatenate = false;
                sqlQuery.append(" WHERE ");
                for (String s : whereClause) {
                    if (concatenate) {
                        sqlQuery.append(" AND ");
                    }else{
                        concatenate = true;
                    }
                    sqlQuery.append(s);
                }
            }

            cursor = db.rawQuery(sqlQuery.toString(), null);

            if (cursor.moveToFirst()) {
                do {
                    Table table = new Table(cursor);
                    TablesList.add(table);
                } while (cursor.moveToNext());
            }

            close(cursor, db);
            return TablesList;
        }

        public synchronized long getTablesCount(String... whereClause) {


            SQLiteDatabase db = getReadableDatabase();
            long count = 0;
            Cursor cursor;
            StringBuilder sqlQuery = new StringBuilder("SELECT * FROM " + TB_Tables);
            if (whereClause != null && whereClause.length > 0) {
                boolean concatenate = false;
                sqlQuery.append(" WHERE ");
                for (String s : whereClause) {
                    if (concatenate) {
                        sqlQuery.append(" AND ");
                    }else{
                        concatenate = true;
                    }
                    sqlQuery.append(s);
                }
            }


            cursor = db.rawQuery(sqlQuery.toString(), null);

            if (cursor.moveToFirst()) {
                count = cursor.getCount();
            }

            close(cursor, db);
            return count;
        }

    //endregion

    //region Add or Update

        public synchronized long addOrUpdateTable(Table table) {
            long id = -1;
            ContentValues value = new ContentValues();
            value.put("id", table.getID());
            value.put("n_posti", table.getChairs());

            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            try {
                long rowCount;
                rowCount = db.update(TB_Tables, value, "id='" + table.getID() + "'", null);
                if (rowCount == 0) {
                    //Il device non esiste --> insert
                    id = db.insertWithOnConflict(TB_Tables, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                    /*if (id == -1) {

                    }*/
                }
            } catch (android.database.sqlite.SQLiteConstraintException ex) {
                throw new SQLiteConstraintException();
            } finally {
                db.setTransactionSuccessful();
                db.endTransaction();
                close(db);
            }
            return id;
        }

    //endregion

    private void loadAutoId(){
        Table.setAuto_id(getTablesCount() + 1);
    }

    private void updateDatabaseVersion(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion > oldVersion){
            switch (oldVersion){

                default:
                    System.err.println("Version : " + oldVersion + " not saved in updateDatabaseVersion()");
            }
        }
    }

    private void createTables(SQLiteDatabase db){
        try {
            //DB_VERSION: 1
            db.execSQL(CREATE_TB_Tables_QUERY);
            db.execSQL(CREATE_TB_Orders_QUERY);
            db.execSQL(CREATE_TB_Orders___Drinks_QUERY);
            db.execSQL(CREATE_TB_Orders___Waiters_QUERY);
            db.execSQL(CREATE_TB_Drinks_QUERY);
            db.execSQL(CREATE_TB_Waiters_QUERY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //region Close db functions
        private void close(Cursor cursor, SQLiteDatabase db) {
            close(cursor);
            close(db);
        }

        private void close(SQLiteDatabase db) {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        private void close(Cursor cursor) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    //endregion

    public static String getDbName(){return DB_NAME;}
    public static int getDbVersion(){return DB_VERSION;}
}
