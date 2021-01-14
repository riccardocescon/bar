package com.keyautomation.mybar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends Activity{

    private ListViewAdapter mAdapter;

    private List<Order> orderList = new ArrayList<>();

    private ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);

        orderList = DatabaseHelper.instance.getOrdersList();

        listview = findViewById(R.id.view_list_element);
        listview.setAdapter(mAdapter = new ListViewAdapter(this, orderList));

    }

}