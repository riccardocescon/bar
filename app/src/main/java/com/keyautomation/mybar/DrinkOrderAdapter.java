package com.keyautomation.mybar;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrinkOrderAdapter extends BaseAdapter {

    private IDeleteButton deleteButtonCallback;
    public interface IDeleteButton{
        void OnButtonDelete(int position);
    }

    private final Activity mContext;
    private final List<Drink> drinks;

    private final String[] values = {"1","2","3","4","5","6","7","8","9"};

    public DrinkOrderAdapter(Context context, List<Drink> drinks){
        mContext = (Activity)context;
        this.drinks = drinks;
        deleteButtonCallback = (IDeleteButton) context;
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
        private Spinner amount_spinner;
        private ImageView delete_button;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        DrinkOrderAdapter.ViewHolder holder;
        final Drink drink = drinks.get(position);
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_order_drink, parent, false);
            holder = new DrinkOrderAdapter.ViewHolder();
            holder.name = view.findViewById(R.id.item_order_drink_name);
            holder.amount_spinner = (Spinner) view.findViewById(R.id.item_order_drink_spinner);
            holder.delete_button = view.findViewById(R.id.item_order_drink_delete);


            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, values);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.amount_spinner.setAdapter(adapter);

            view.setTag(holder);
        }else{
            holder = (DrinkOrderAdapter.ViewHolder) view.getTag();
        }

        holder.amount_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                drink.setQuantity(++i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.amount_spinner.setSelection(drink.getQuantity() - 1);

        holder.name.setText(drink.getName());

        holder.delete_button.setOnClickListener(e ->{
            deleteButtonCallback.OnButtonDelete(position);
        });

        return view;
    }


}
