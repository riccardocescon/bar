package com.keyautomation.mybar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private final Activity mContext;
    private final List<Order> orders;

    public ListViewAdapter(Context context, List<Order> orders){
        mContext = (Activity)context;
        this.orders = orders;
    }

    @Override
    public int getCount() { return orders.size(); }

    @Override
    public Object getItem(int position) {
        if(position > orders.size() - 1) return null;
        return orders.get(position);
    }

    @Override
    public long getItemId(int pos) { return pos; }

    private static class ViewHolder {
        private TextView n_table;
        private TextView elements;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
            holder = new ViewHolder();
            holder.n_table = view.findViewById(R.id.item_order_n_table);
            holder.elements = view.findViewById(R.id.item_order_elements);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Order order = orders.get(position);
        Log.d("OrderInfo", String.valueOf(order.getID()));
        Log.d("OrderInfo", String.valueOf(order.getServed()));
        Log.d("OrderInfo", String.valueOf(order.getTableId()));
        Log.d("OrderInfo_pos", String.valueOf(position));
        //if(order == null)return view;

        holder.n_table.setText("Table " + order.getTableId());

        List<Drink> drinks = order.getDrinks();//get only drink of the same table
        String elements = "";
        if(drinks.size() < 3){
            for(int i = 0; i < drinks.size(); i++){
                elements += drinks.get(i).getName();
                if(i < drinks.size() - 1) elements += "\n";
            }
        }else{
            elements = drinks.get(0).getName() + "\n.....";
        }

        holder.elements.setText(elements);

        return view;
    }
}
