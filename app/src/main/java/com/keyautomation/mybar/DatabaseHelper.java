package com.keyautomation.mybar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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

    private static final String DB_NAME = "dbbar";
    private static final int DB_VERSION = 1;

    //region Tabelle
    private static final String TB_Tables = "tb_tables";
    private static final String TB_Orders = "tb_orders";
    private static final String TB_Orders_Drinks = "tb_orders_drinks";
    private static final String TB_Orders_Waiters = "tb_orders_waiters";
    private static final String TB_Orders_Tables = "tb_orders_tables";
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
    public static final String FLD_Orders___Drinks_FK_DRINK_AMOUNT = "drink_amount";
    //endregion

    //region Order-Waiter
    public static final String FLD_Orders___Waiters_FK_Order = "fk_order";
    public static final String FLD_Orders___Waiters_FK_Waiter = "fk_waiter";
    //endregion

    //region Order-Tables
    public static final String FLD_Orders___Tables_FK_Order = "fk_order";
    public static final String FLD_Orders___Tables_FK_Table = "fk_table";
    //endregion

    //endregion

    //region Creazione Tabelle

    private static final String CREATE_TB_Tables_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Tables + " (" +
                    FLD_Table_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    FLD_Table_N_CHAIRS + " VARCHAR(20)" +
                    ")";

    private static final String CREATE_TB_Orders_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders + " (" +
                    FLD_Orders_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    FLD_Orders_SERVED + " INTEGER ," +
                    FLD_Orders_PAID + " INTEGER" +
                    ")";

    private static final String CREATE_TB_Drinks_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Drinks + " (" +
                    FLD_Drink_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    FLD_Drink_NAME + " FLOAT ," +
                    FLD_Drink_ALCOHOL + " FLOAT ," +
                    FLD_Drink_PRICE + " FLOAT " +
                    ")";

    private static final String CREATE_TB_Waiters_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Waiters + " (" +
                    FLD_Waiter_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    FLD_Waiter_NAME + " VARCHAR(20) ," +
                    FLD_Waiter_PASSWORD + " VARCHAR(100)," +
                    FLD_Waiter_BORN + " LONG," +
                    FLD_Waiter_BEGIN_WORKING + " LONG," +
                    FLD_Waiter_SALARY + " FLOAT" +
                    ")";

    private static final String CREATE_TB_Orders___Drinks_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders_Drinks + " (" +
                    FLD_Orders___Drinks_FK_Order + " LONG, " +
                    FLD_Orders___Drinks_FK_Drink + " LONG, " +
                    FLD_Orders___Drinks_FK_DRINK_AMOUNT + " INTEGER " +
                    ")";

    private static final String CREATE_TB_Orders___Waiters_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders_Waiters + " (" +
                    FLD_Orders___Waiters_FK_Order + " LONG, " +
                    FLD_Orders___Waiters_FK_Waiter + " LONG " +
                    ")";

    private static final String CREATE_TB_Orders___Tables_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TB_Orders_Tables + " (" +
                    FLD_Orders___Tables_FK_Order + " LONG, " +
                    FLD_Orders___Tables_FK_Table + " LONG " +
                    ")";

    //endregion

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static DatabaseHelper mInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
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

    public synchronized Table getTableByOrder(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        Table table = null;
        //StringBuilder sqlQuery_fk_table = getSqlQuery(TB_Orders_Tables, whereClause);

        StringBuilder request = new StringBuilder("SELECT * ");
        request.append("FROM " + TB_Orders_Tables + " JOIN " + TB_Tables + " ");
        request.append("ON " + FLD_Table_ID + " = " + FLD_Orders___Tables_FK_Table);

        if (whereClause != null && whereClause.length > 0) {
            boolean concatenate = false;
            request.append(" WHERE ");
            for (String s : whereClause) {
                if (concatenate) {
                    request.append(" AND ");
                } else {
                    concatenate = true;
                }
                request.append(s);
            }
        }

        //cursor = db.rawQuery(sqlQuery_fk_table.toString(), null);
        Log.d("Query__", request.toString());
        cursor = db.rawQuery(request.toString(), null);

        if (cursor.moveToFirst()) {
            table = new Table(cursor);
            /*Orders_Tables orders_tables = new Orders_Tables(cursor);

            StringBuilder sqlQuery = getSqlQuery(TB_Tables, FLD_Table_ID + " = " + orders_tables.getFkTable());

            cursor = db.rawQuery(sqlQuery.toString(), null);

            if (cursor.moveToFirst()) {

                table = new Table(cursor);
            }*/
        }

        close(cursor, db);
        return table;
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

    public synchronized Waiter getWaiterByOrder(String... whereClause) {
        Log.d("WaiterSQL", whereClause.toString());
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        Waiter waiter = null;
        //StringBuilder sqlQuery_fk_waiter = getSqlQuery(TB_Orders_Waiters, whereClause);

        StringBuilder request = new StringBuilder("SELECT * ");
        request.append("FROM " + TB_Orders_Waiters + " JOIN " + TB_Waiters + " ");
        request.append("ON " + FLD_Waiter_ID + " = " + FLD_Orders___Waiters_FK_Waiter);

        //cursor = db.rawQuery(sqlQuery_fk_waiter.toString(), null);
        cursor = db.rawQuery(request.toString(), null);

        if (cursor.moveToFirst()) {
            waiter = new Waiter(cursor);
            /*
            Orders_Waiters orders_tables = new Orders_Waiters(cursor);

            StringBuilder sqlQuery = getSqlQuery(TB_Waiters, FLD_Waiter_ID + " = " + orders_tables.getFkWaiter());

            cursor = db.rawQuery(sqlQuery.toString(), null);

            if (cursor.moveToFirst()) {
                waiter = new Waiter(cursor);
            }*/
        }

        close(cursor, db);
        return waiter;
    }

    public synchronized long getOrdersCount(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        long count = 0;
        Cursor cursor;
        StringBuilder sqlQuery = getSqlQuery(TB_Orders, whereClause);

        cursor = db.rawQuery(sqlQuery.toString(), null);

        if (cursor.moveToFirst()) {
            count = cursor.getCount();
        }

        close(cursor, db);
        return count;
    }
    //endregion

    //region Drinks

    public synchronized List<Drink> getDrinksList(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        List<Drink> drinksList = new ArrayList<>();
        Cursor cursor;
        StringBuilder sqlQuery = getSqlQuery(TB_Drinks, whereClause);

        cursor = db.rawQuery(sqlQuery.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                Drink drink = new Drink(cursor);
                drinksList.add(drink);
            } while (cursor.moveToNext());
        }

        close(cursor, db);
        return drinksList;
    }

    public synchronized long getDrinksCount(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        long count = 0;
        Cursor cursor;
        StringBuilder sqlQuery = getSqlQuery(TB_Drinks, whereClause);

        cursor = db.rawQuery(sqlQuery.toString(), null);

        if (cursor.moveToFirst()) {
            count = cursor.getCount();
        }

        close(cursor, db);
        return count;
    }

    public synchronized List<Drink> getDrinksByTableList(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        List<Drink> drinksList = new ArrayList<>();
        Cursor cursor;
        StringBuilder request = new StringBuilder("SELECT * ");
        request.append("FROM " + TB_Orders_Drinks + " JOIN " + TB_Drinks);
        request.append(" ON " + FLD_Drink_ID + " = " + FLD_Orders___Drinks_FK_Drink);
        request.append(" WHERE " + FLD_Orders___Drinks_FK_Order + " = ");
        request.append("( SELECT " + FLD_Orders___Tables_FK_Order);
        request.append(" FROM " + TB_Tables + " JOIN " + TB_Orders_Tables);
        request.append(" ON " + FLD_Table_ID + " = " + FLD_Orders___Tables_FK_Table);

        if (whereClause != null && whereClause.length > 0) {
            boolean concatenate = false;
            request.append(" WHERE ");
            for (String s : whereClause) {
                if (concatenate) {
                    request.append(" AND ");
                } else {
                    concatenate = true;
                }
                request.append(s);
            }
        }

        request.append(" ) ");
        //StringBuilder sqlQuery = getSqlQuery(TB_Drinks, whereClause);

        //cursor = db.rawQuery(sqlQuery.toString(), null);
        cursor = db.rawQuery(request.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                Drink drink = new Drink(cursor);
                drinksList.add(drink);
            } while (cursor.moveToNext());
        }

        close(cursor, db);
        return drinksList;
    }

    //endregion


    public synchronized List<Drink> getDrinksByOrder(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor, cursorDrink;
        List<Drink> drinks = new ArrayList<>();

        //StringBuilder sqlQuery_fk_drink = getSqlQuery(TB_Orders_Drinks, whereClause);

        StringBuilder request = new StringBuilder("SELECT * ");
        request.append("FROM " + TB_Orders_Drinks + " JOIN " + TB_Drinks + " ");
        request.append("ON " + FLD_Drink_ID + " = " + FLD_Orders___Drinks_FK_Drink);

        if (whereClause != null && whereClause.length > 0) {
            boolean concatenate = false;
            request.append(" WHERE ");
            for (String s : whereClause) {
                if (concatenate) {
                    request.append(" AND ");
                } else {
                    concatenate = true;
                }
                request.append(s);
            }
        }

        //cursor = db.rawQuery(sqlQuery_fk_drink.toString(), null);
        cursor = db.rawQuery(request.toString(), null);


        if (cursor.moveToFirst()) {
            do {
                Drink drink = new Drink(cursor);
                drinks.add(drink);
                /*Orders_Drinks orders_drinks = new Orders_Drinks(cursor);

                StringBuilder sqlQuery = getSqlQuery(TB_Drinks, FLD_Drink_ID + " = " + orders_drinks.getFkDrink());

                cursorDrink = db.rawQuery(sqlQuery.toString(), null);

                if (cursorDrink.moveToFirst()) {
                    do {
                        Drink drink = new Drink(cursorDrink);
                        drinks.add(drink);
                    } while (cursorDrink.moveToNext());
                }
                */
            } while (cursor.moveToNext());
        }

        close(cursor, db);

        return drinks;
    }

    //region Orders

    public synchronized List<Order> getOrdersList(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        List<Order> ordersList = new ArrayList<>();
        Cursor cursor = null;
        StringBuilder sqlQuery = getSqlQuery(TB_Orders, whereClause);

        try {
            cursor = db.rawQuery(sqlQuery.toString(), null);

            if (cursor.moveToFirst()) {
                do {
                    Order order = new Order(cursor);
                    ordersList.add(order);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(cursor, db);
        }

        for (Order o : ordersList) {
            List<Drink> drinks = getDrinksByOrder("fk_order = " + o.id);
            long table_id = getTableByOrder("fk_order = " + o.id).getID();
            o.setDrinkTables(drinks, table_id);
        }

        return ordersList;
    }

    //endregion

    public synchronized List<Orders_Tables> getOrderTablesList(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        List<Orders_Tables> TablesList = new ArrayList<>();
        Cursor cursor;
        StringBuilder sqlQuery = getSqlQuery(TB_Orders_Tables, whereClause);

        cursor = db.rawQuery(sqlQuery.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                Orders_Tables table = new Orders_Tables(cursor);
                TablesList.add(table);
            } while (cursor.moveToNext());
        }

        close(cursor, db);
        return TablesList;
    }

    public synchronized List<Orders_Drinks> getOrderDrinksList(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        List<Orders_Drinks> TablesList = new ArrayList<>();
        Cursor cursor;
        StringBuilder sqlQuery = getSqlQuery(TB_Orders_Drinks, whereClause);

        cursor = db.rawQuery(sqlQuery.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                Orders_Drinks table = new Orders_Drinks(cursor);
                TablesList.add(table);
            } while (cursor.moveToNext());
        }

        close(cursor, db);
        return TablesList;
    }

    public synchronized List<Orders_Waiters> getOrderWaitersList(String... whereClause) {
        SQLiteDatabase db = getReadableDatabase();
        List<Orders_Waiters> TablesList = new ArrayList<>();
        Cursor cursor;
        StringBuilder sqlQuery = getSqlQuery(TB_Orders_Waiters, whereClause);

        cursor = db.rawQuery(sqlQuery.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                Orders_Waiters table = new Orders_Waiters(cursor);
                TablesList.add(table);
            } while (cursor.moveToNext());
        }

        close(cursor, db);
        return TablesList;
    }

    //endregion

    //region Add or Update

    public synchronized long addOrUpdateTable(Table table) {
        long id = -1;
        ContentValues value = new ContentValues();
        if (table.getID() > 0)
            value.put(FLD_Table_ID, table.getID());
        value.put(FLD_Table_N_CHAIRS, table.getChairs());

        String TABLE = TB_Tables;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            //long rowCount;
            //Funzione per gestire stringhe in query senza preoccuparsi delle virgolette
            //String waiterName = DatabaseUtils.sqlEscapeString(name);
            //rowCount = db.update(TABLE, value, FLD_Table_ID + " =" + table.getID(), null);
            //if (rowCount == 0) {
            //Il device non esiste --> insert
            id = db.insertWithOnConflict(TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                    /*if (id == -1) {

                    }*/
            //}
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
        value.put(FLD_Waiter_NAME, waiter.getName());
        value.put(FLD_Waiter_PASSWORD, waiter.getPassword());
        value.put(FLD_Waiter_BORN, waiter.getBorn());
        value.put(FLD_Waiter_BEGIN_WORKING, waiter.getBeginWorking());
        value.put(FLD_Waiter_SALARY, waiter.getSalary());

        String TABLE = TB_Waiters;

        //id = addOrUpdateGeneric(TB_Waiters, value, waiter);

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            //long rowCount;
            //rowCount = db.update(TABLE, value, FLD_Waiter_ID + " =" + waiter.getID(), null);
            //if (rowCount == 0) {
            //Il device non esiste --> insert
            id = db.insertWithOnConflict(TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);
            //}
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            throw new SQLiteConstraintException();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
        }
        return id;
    }

    public synchronized long addOrUpdateDrink(Drink drink) {
        long id = -1;
        ContentValues value = new ContentValues();
        if(drink.getID() > 0)
            value.put(FLD_Drink_ID, drink.getID());
        value.put(FLD_Drink_NAME, drink.getName());
        value.put(FLD_Drink_ALCOHOL, drink.getAlcohol());
        value.put(FLD_Drink_PRICE, drink.getPrice());

        String TABLE = TB_Drinks;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            //long rowCount;
            //Funzione per gestire stringhe in query senza preoccuparsi delle virgolette
            //String waiterName = DatabaseUtils.sqlEscapeString(name);
            //rowCount = db.update(TABLE, value, FLD_Drink_ID + " =" + drink.getID(), null);
            //if (rowCount == 0) {
            //Il device non esiste --> insert
            id = db.insertWithOnConflict(TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        /*if (id == -1) {

                        }*/
            //}
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            throw new SQLiteConstraintException();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
        }
        return id;
    }

    public synchronized long addOrUpdateOrder(Order order) {
        long id = -1;
        ContentValues value = new ContentValues();
        if(order.getID() > 0)
            value.put(FLD_Orders_ID, order.getID());
        value.put(FLD_Orders_SERVED, order.getServed());
        value.put(FLD_Orders_PAID, order.getPaid());

        String TABLE = TB_Orders;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            //long rowCount;
            //Funzione per gestire stringhe in query senza preoccuparsi delle virgolette
            //String waiterName = DatabaseUtils.sqlEscapeString(name);
            //rowCount = db.update(TABLE, value, FLD_Orders_ID + " =" + order.getID(), null);
            //if (rowCount == 0) {
            //Il device non esiste --> insert
            id = db.insertWithOnConflict(TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);
            //}
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            ex.printStackTrace();
            throw new SQLiteConstraintException();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
        }
        return id;
    }

    public synchronized long addOrUpdateOrderTable(Orders_Tables order_table) {
        long id = -1;
        ContentValues value = new ContentValues();
        value.put(FLD_Orders___Tables_FK_Order, order_table.getFkOrder());
        value.put(FLD_Orders___Tables_FK_Table, order_table.getFkTable());

        String TABLE = TB_Orders_Tables;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            //long rowCount;
            //Funzione per gestire stringhe in query senza preoccuparsi delle virgolette
            //String waiterName = DatabaseUtils.sqlEscapeString(name);
            //rowCount = db.update(TABLE, value, FLD_Orders___Tables_FK_Order + " =" + order_table.getFkOrder(), null);
            //if (rowCount == 0) {
            //Il device non esiste --> insert
            id = db.insertWithOnConflict(TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);
                                /*if (id == -1) {

                                }*/
            //}
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            ex.printStackTrace();
            throw new SQLiteConstraintException();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
        }
        return id;
    }

    public synchronized long addOrUpdateOrderDrinks(Orders_Drinks order_drinks) {
        long id = -1;
        ContentValues value = new ContentValues();
        value.put(FLD_Orders___Drinks_FK_Order, order_drinks.getFkOrder());
        value.put(FLD_Orders___Drinks_FK_Drink, order_drinks.getFkDrink());
        //value.put(FLD_Orders___Drinks_FK_DRINK_AMOUNT, order_drinks.getDrinkAmount());

        String TABLE = TB_Orders_Drinks;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            id = db.insertWithOnConflict(TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            ex.printStackTrace();
            throw new SQLiteConstraintException();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
        }
        return id;
    }

    public synchronized long addOrUpdateOrderWaiters(Orders_Waiters order_waiters) {
        long id = -1;
        ContentValues value = new ContentValues();
        value.put(FLD_Orders___Waiters_FK_Order, order_waiters.getFkOrder());
        value.put(FLD_Orders___Waiters_FK_Waiter, order_waiters.getFkWaiter());

        String TABLE = TB_Orders_Waiters;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            id = db.insertWithOnConflict(TABLE, null, value, SQLiteDatabase.CONFLICT_REPLACE);
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

    private long addOrUpdateGeneric(String table, ContentValues value, long _id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long id = -1;
        try {
            long rowCount;
            rowCount = db.update(table, value, "id=" + _id, null);
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

    private StringBuilder getSqlQuery(String table, String... whereClause) {
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM " + table);
        if (whereClause != null && whereClause.length > 0) {
            boolean concatenate = false;
            sqlQuery.append(" WHERE ");
            for (String s : whereClause) {
                if (concatenate) {
                    sqlQuery.append(" AND ");
                } else {
                    concatenate = true;
                }
                sqlQuery.append(s);
            }
        }
        return sqlQuery;
    }

    private void updateDatabaseVersion(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            switch (oldVersion) {

                default:
                    System.err.println("Version : " + oldVersion + " not saved in updateDatabaseVersion()");
            }
        }
    }

    private void createTables(SQLiteDatabase db) {
        try {
            //DB_VERSION: 1
            db.execSQL(CREATE_TB_Tables_QUERY);
            db.execSQL(CREATE_TB_Orders_QUERY);
            db.execSQL(CREATE_TB_Orders___Drinks_QUERY);
            db.execSQL(CREATE_TB_Orders___Waiters_QUERY);
            db.execSQL(CREATE_TB_Orders___Tables_QUERY);
            db.execSQL(CREATE_TB_Drinks_QUERY);
            db.execSQL(CREATE_TB_Waiters_QUERY);
        } catch (Exception e) {
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

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }
}
