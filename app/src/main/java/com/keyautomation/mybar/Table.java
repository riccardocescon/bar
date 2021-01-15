package com.keyautomation.mybar;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Table extends AutoId implements Parcelable {

    private int n_chairs;

    public Table(int n_chairs){
        super();
        id = auto_table_id;
        auto_table_id++;

        this.n_chairs = n_chairs;
    }

    public Table(Cursor cursor){
        super();
        try{
            this.id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.FLD_Table_ID));
            this.n_chairs = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLD_Table_N_CHAIRS));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setChairs(int n_chairs){this.n_chairs = n_chairs;}
    public int getChairs(){return n_chairs;}

    protected Table(Parcel in) {
        super(in);
        n_chairs = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        super.writeToParcel(dest);
        dest.writeInt(n_chairs);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };
}
