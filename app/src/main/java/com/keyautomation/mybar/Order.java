package com.keyautomation.mybar;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Order implements Parcelable {

    public long id;
    private int served;
    private int paid;

    private Context context;

    //vars not inside database
    private List<Drink> drinks = new ArrayList<>();
    private long table_id;

    public Order(Context context, int served, int paid){
        this.context = context;
        this.served = served;
        this.paid = paid;
    }

    public Order(Cursor cursor){
        try{
            id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_ID));
            served = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_SERVED));
            paid = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_PAID));
            //drinks = DatabaseHelper.instance.getDrinksByOrder("fk_order = " + id);
            //table_id = DatabaseHelper.instance.getTableByOrder( "fk_order = " + id).getID();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setDrinkTables(List<Drink> drinks, long table_id){
        this.drinks = drinks;
        this.table_id = table_id;
    }

    public void assignRemainingVars(){
        drinks = DatabaseHelper.getInstance(context).getDrinksByOrder("fk_order = " + id);
        table_id = DatabaseHelper.getInstance(context).getTableByOrder( "fk_order = " + id).getID();
    }

    public int getServed(){ return served; }
    public void setPaid(int paid){ this.paid = paid;}
    public int getPaid(){ return paid; }
    public void addDrink(Drink drink){ drinks.add(drink); }
    public List<Drink> getDrinks(){
        String request = DatabaseHelper.FLD_Orders___Tables_FK_Order + " = " + id;
        long table_id = DatabaseHelper.getInstance(context).getTableByOrder(request).getID();

        request = DatabaseHelper.FLD_Orders___Tables_FK_Table + " = " + table_id;
        List<Drink> drinks = DatabaseHelper.getInstance(context).getDrinksByTableList(request);
        this.drinks = drinks;
        return drinks;
    }
    public long getTableId(){ return table_id; }
    public long getID(){return id;}
    public void setID(long id){this.id = id;}

    protected Order(Parcel in) {
        id = in.readLong();
        served = in.readInt();
        paid = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(id);
        dest.writeInt(served);
        dest.writeInt(paid);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

}
