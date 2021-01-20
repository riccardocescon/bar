package com.keyautomation.mybar;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Drink implements Parcelable {

    private long id;
    private String name;
    private float alcohol;
    private float price;

    private int quantity;

    public Drink(String name, float alcohol, float price){
        this.name = name;
        this.alcohol = alcohol;
        this.price = price;

        quantity = 1;
    }

    public Drink(Cursor cursor){
        this.id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Drink_ID));
        this.name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FLD_Drink_NAME));
        this.alcohol = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.FLD_Drink_ALCOHOL));
        this.price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.FLD_Drink_PRICE));
    }

    public String getName(){ return name; }
    public float getAlcohol(){ return alcohol; }
    public float getPrice(){ return price; }
    public long getID(){return id;}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void increaseQuantity() {
        this.quantity++;
    }

    protected Drink(Parcel in) {
        name = in.readString();
        alcohol = in.readFloat();
        price = in.readFloat();
        quantity = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeFloat(alcohol);
        dest.writeFloat(price);
        dest.writeInt(quantity);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Float.compare(drink.alcohol, alcohol) == 0 &&
                Float.compare(drink.price, price) == 0 &&
                name.equals(drink.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alcohol, price);
    }
}
