package com.keyautomation.mybar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

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
            public static final String FLD_Table_N_CHAIRS = "n_chairs";
        //endregion

        //region Drink
            public static final String FLD_Drink_ID = "id";
            public static final String FLD_Drink_NAME = "name";
            public static final String FLD_Drink_ALCOHOL = "alcohol";
            public static final String FLD_Drink_PRICE = "price";
        //endregion

        //region Waiter
            public static final String FLD_Waiter_ID = "id";
            public static final String FLD_Waiter_NAME = "name";
            public static final String FLD_Waiter_PASSWORD = "password";
            public static final String FLD_Waiter_BORN = "born";
            public static final String FLD_Waiter_BEGIN_WORKING = "begin_working";
            public static final String FLD_Waiter_SALARY = "salary";
        //endregion

        //region Orders
            public static final String FLD_Orders_ID = "id";
            public static final String FLD_Orders_SERVED = "served";
            public static final String FLD_Orders_PAID = "paid";
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
                    FLD_Table_N_CHAIRS + " VARCHAR(20)" +
                    ")";

        private static final String CREATE_TB_Orders_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders + " (" +
                    FLD_Orders_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_Orders_SERVED + " BOOLEAN ," +
                    FLD_Orders_PAID + " BOOLEAN" +
                    ")";

        private static final String CREATE_TB_Drinks_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Drinks + " (" +
                    FLD_Drink_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_Drink_NAME + " FLOAT ," +
                    FLD_Drink_ALCOHOL + " FLOAT ," +
                    FLD_Drink_PRICE + " FLOAT "+
                    ")";

        private static final String CREATE_TB_Waiters_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Waiters + " (" +
                    FLD_Waiter_ID + " INTEGER  PRIMARY KEY , " +
                    FLD_Waiter_NAME + " VARCHAR(20) ," +
                    FLD_Waiter_PASSWORD + " VARCHAR(100)," +
                    FLD_Waiter_BORN + " LONG," +
                    FLD_Waiter_BEGIN_WORKING + " LONG," +
                    FLD_Waiter_SALARY + " FLOAT" +
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
        //region Tables
            public synchronized List<Table> getTablesList(String... whereClause) {
                SQLiteDatabase db = getReadableDatabase();
                List<Table> TablesList = new ArrayList<>();
                Cursor cursor;
                StringBuilder sqlQuery = getSqlQuery(TB_Tables, whereClause);

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
                StringBuilder sqlQuery = getSqlQuery(TB_Tables, whereClause);

                cursor = db.rawQuery(sqlQuery.toString(), null);

                if (cursor.moveToFirst()) {
                    count = cursor.getCount();
                }

                close(cursor, db);
                return count;
            }

            //endregion

            //region Waiters

                public synchronized List<Waiter> getWaitersList(String... whereClause) {
                    SQLiteDatabase db = getReadableDatabase();
                    List<Waiter> waitersList = new ArrayList<>();
                    Cursor cursor;
                    StringBuilder sqlQuery = getSqlQuery(TB_Waiters, whereClause);

                    cursor = db.rawQuery(sqlQuery.toString(), null);

                    if (cursor.moveToFirst()) {
                        do {
                            Waiter waiter = new Waiter(cursor);
                            waitersList.add(waiter);
                        } while (cursor.moveToNext());
                    }

                    close(cursor, db);
                    return waitersList;
                }

                public synchronized long getWaitersCount(String... whereClause) {
                    SQLiteDatabase db = getReadableDatabase();
                    long count = 0;
                    Cursor cursor;
                    StringBuilder sqlQuery = getSqlQuery(TB_Waiters, whereClause);

                    cursor = db.rawQuery(sqlQuery.toString(), null);

                    if (cursor.moveToFirst()) {
                        count = cursor.getCount();
                    }

                    close(cursor, db);
                    return count;
                }

            //endregion

    //endregion

    //region Add or Update

        public synchronized long addOrUpdateTable(Table table) {
            long id = -1;
            ContentValues value = new ContentValues();
            value.put(FLD_Table_ID, table.getID());
            value.put(FLD_Table_N_CHAIRS, table.getChairs());

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public synchronized long addOrUpdateWaiter(Waiter waiter) {
        long id = 0;
        ContentValues value = new ContentValues();
        value.put(FLD_Waiter_ID, waiter.getID());
        value.put(FLD_Waiter_NAME, waiter.getName());
        value.put(FLD_Waiter_PASSWORD, waiter.getPassword());
        value.put(FLD_Waiter_BORN, waiter.getBorn());
        value.put(FLD_Waiter_BEGIN_WORKING, waiter.getBeginWorking());
        value.put(FLD_Waiter_SALARY, waiter.getSalary());

        //id = addOrUpdateGeneric(TB_Waiters, value, waiter);

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            long rowCount;
            System.out.println("Checking id : " + waiter.getID());
            rowCount = db.update(TB_Waiters, value, "id='" + waiter.getID() + "'", null);
            if (rowCount == 0) {
                //Il device non esiste --> insert
                System.out.println("Adding : " + waiter.getName());
                id = db.insertWithOnConflict(TB_Waiters, null, value, SQLiteDatabase.CONFLICT_REPLACE);
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

    private long addOrUpdateGeneric(String table, ContentValues value, AutoId element){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long id = -1;
        try {
            long rowCount;
            rowCount = db.update(table, value, "id='" + element.getID() + "'", null);
            if (rowCount == 0) {
                //Il device non esiste --> insert
                id = db.insertWithOnConflict(table, null, value, SQLiteDatabase.CONFLICT_REPLACE);
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

    private StringBuilder getSqlQuery(String table, String... whereClause){
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM " + table);
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
        return sqlQuery;
    }

    private void loadAutoId(){
        Table.setAutoTableId(getTablesCount() + 1);
        Waiter.setAutoWaiterId(getWaitersCount() + 1);
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
