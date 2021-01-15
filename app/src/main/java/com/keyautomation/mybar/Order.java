package com.keyautomation.mybar;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Order extends AutoId implements Parcelable {

    private int served;
    private int paid;

    //vars not inside database
    private List<Drink> drinks = new ArrayList<>();
    private long table_id;

    public Order(int served, int paid){
        id = auto_order_id;
        auto_order_id++;

        this.served = served;
        this.paid = paid;
    }

    public Order(Cursor cursor){
        try{
            id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_ID));
            served = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_SERVED));
            paid = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Orders_PAID));
            drinks = DatabaseHelper.instance.getDrinksByOrder("fk_order = " + id);
            table_id = DatabaseHelper.instance.getTableByOrder( "fk_order = " + id).getID();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void assignRemainingVars(){
        drinks = DatabaseHelper.instance.getDrinksByOrder("fk_order = " + id);
        table_id = DatabaseHelper.instance.getTableByOrder( "fk_order = " + id).getID();
    }

    public int getServed(){ return served; }
    public int getPaid(){ return paid; }
    public void addDrink(Drink drink){ drinks.add(drink); }
    public List<Drink> getDrinks(){ return drinks; }
    public long getTableId(){ return table_id; }

    protected Order(Parcel in) {
        super(in);
        served = in.readInt();
        paid = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        super.writeToParcel(dest);
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
