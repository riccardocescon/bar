package com.keyautomation.mybar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemInfoAdapter extends BaseAdapter {
    private final Activity mContext;
    private final List<Drink> drinks;

    Map<Drink, Integer> unique_drinks = new HashMap<>();

    private final long order_id;

    public ItemInfoAdapter(Context context, List<Drink> drinks, long order_id){
        mContext = (Activity)context;
        this.drinks = drinks;
        this.order_id = order_id;
    }

    @Override
    public int getCount() { return drinks.size(); }

    @Override
    public Object getItem(int position) {
        if(position > drinks.size() - 1) return null;
        return drinks.get(position);
    }

    @Override
    public long getItemId(int pos) { return pos; }

    private static class ViewHolder {
        private TextView name;
        private TextView price;
        private TextView amount;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemInfoAdapter.ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_drink, parent, false);
            holder = new ItemInfoAdapter.ViewHolder();
            holder.name = view.findViewById(R.id.item_drink_name);
            holder.price = view.findViewById(R.id.item_drink_price);
            holder.amount = view.findViewById(R.id.item_drink_amount);
            view.setTag(holder);
        }else{
            holder = (ItemInfoAdapter.ViewHolder) view.getTag();
        }

        Drink drink = drinks.get(position);

        if(!isUnique(unique_drinks, drink)){
            ((ViewManager)holder.name.getParent()).removeView(holder.name);
            ((ViewManager)holder.price.getParent()).removeView(holder.price);
            ((ViewManager)holder.amount.getParent()).removeView(holder.amount);
            return view;
        }

        holder.name.setText(drink.getName());

        holder.price.setText(String.valueOf(drink.getPrice()));

        String request = DatabaseHelper.FLD_Orders___Drinks_FK_Order + " = " + order_id + " AND " +
                DatabaseHelper.FLD_Orders___Drinks_FK_Drink + " = " + drink.getID();

        List<Drink> drinks = DatabaseHelper.getInstance(mContext).getDrinksByOrder(request);

        for(Drink d : drinks){
            if(isUnique(unique_drinks, d)){
                unique_drinks.put(d, 1);
            }else{
                for(Map.Entry<Drink, Integer> map : unique_drinks.entrySet()){
                    if(map.getKey().getName().equals(d.getName())){
                        unique_drinks.put(map.getKey(), map.getValue() + 1);
                    }
                }
            }
        }

        holder.amount.setText(String.valueOf(unique_drinks.get(drink)));
        unique_drinks = new LinkedHashMap<>();

        return view;
    }

    private boolean isUnique(Map<Drink, Integer> drinks, Drink drink){
        for(Map.Entry<Drink, Integer> map : drinks.entrySet()){
            if(map.getKey().getName().equals(drink.getName()))return false;
        }
        return true;
    }
}
