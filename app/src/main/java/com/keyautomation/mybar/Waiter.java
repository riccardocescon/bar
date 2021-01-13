package com.keyautomation.mybar;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;

public class Waiter extends  AutoId{

    private String name, password;
    private Date born, begin_working;
    private float salary;

    public Waiter(String name, String password, Date born, Date begin_working, float salary){
        //load auto_id
        id = auto_waiter_id;
        auto_waiter_id++;
        System.out.println("waiter : " + name + " has id : " + id);

        this.name = name;
        this.password = password;
        this.born = born;
        this.begin_working = begin_working;
        this.salary = salary;
    }

    public Waiter(Cursor cursor){
        try{
            this.id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Waiter_ID));
            this.name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FLD_Waiter_NAME));
            this.password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FLD_Waiter_PASSWORD));
            this.born = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Waiter_BORN)));
            this.begin_working = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Waiter_BEGIN_WORKING)));
            this.salary = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.FLD_Waiter_SALARY));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getName(){return name;}
    public String getPassword(){return password;}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getBorn(){return born.toInstant().getEpochSecond();}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getBeginWorking(){return begin_working.toInstant().getEpochSecond();}
    public float getSalary(){return salary;}

}
