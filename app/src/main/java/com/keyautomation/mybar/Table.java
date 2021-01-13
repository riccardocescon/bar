package com.keyautomation.mybar;

import android.database.Cursor;

public class Table extends AutoId{

    private int n_chairs;

    public Table(int n_chairs){
        id = auto_table_id;
        auto_table_id++;

        this.n_chairs = n_chairs;
    }

    public Table(Cursor cursor){
        try{
            this.id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Table_ID));
            this.n_chairs = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Table_N_CHAIRS));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setChairs(int n_chairs){this.n_chairs = n_chairs;}
    public int getChairs(){return n_chairs;}

}
