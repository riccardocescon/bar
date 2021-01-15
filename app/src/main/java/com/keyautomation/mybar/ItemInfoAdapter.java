package com.keyautomation.mybar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemInfoAdapter extends BaseAdapter {
    private final Activity mContext;
    private final List<Drink> drinks;

    public ItemInfoAdapter(Context context, List<Drink> drinks){
        mContext = (Activity)context;
        this.drinks = drinks;
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
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemInfoAdapter.ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_drink, parent, false);
            holder = new ItemInfoAdapter.ViewHolder();
            holder.name = view.findViewById(R.id.item_drink_name);
            holder.price = view.findViewById(R.id.item_drink_price);
            view.setTag(holder);
        }else{
            holder = (ItemInfoAdapter.ViewHolder) view.getTag();
        }

        Drink drink = drinks.get(position);

        holder.name.setText(drink.getName());

        holder.price.setText(String.valueOf(drink.getPrice()));

        return view;
    }
}
