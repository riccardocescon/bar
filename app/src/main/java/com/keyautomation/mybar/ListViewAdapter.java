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
        this.orders.forEach(o -> Log.d("Orderid", String.valueOf(o.getID())));
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
        //String clasue = "id = " + order.getID();

        //holder.n_table.setText("Table " + (DatabaseHelper.instance.getTableByOrder(clasue)).getID());
        holder.n_table.setText("Table " + order.getTableId());

        //List<Drink> drinks = DatabaseHelper.instance.getDrinksByOrder(clasue);
        List<Drink> drinks = order.getDrinks();
        drinks.forEach(d -> Log.d("dringid", d.getName()));
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
