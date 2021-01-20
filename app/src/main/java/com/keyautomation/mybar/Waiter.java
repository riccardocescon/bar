package com.keyautomation.mybar;

import android.database.Cursor;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Date;

public class Waiter implements Parcelable {
    private long id;
    private String name, password;
    private Date born, begin_working;
    private float salary;

    public Waiter(String name, String password, Date born, Date begin_working, float salary){
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

    protected Waiter(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(id);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Waiter> CREATOR = new Parcelable.Creator<Waiter>() {
        @Override
        public Waiter createFromParcel(Parcel in) {
            return new Waiter(in);
        }

        @Override
        public Waiter[] newArray(int size) {
            return new Waiter[size];
        }
    };

    public String getName(){return name;}
    public String getPassword(){return password;}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getBorn(){return born.toInstant().getEpochSecond();}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getBeginWorking(){return begin_working.toInstant().getEpochSecond();}
    public float getSalary(){return salary;}
    public long getID(){return id;}

}
