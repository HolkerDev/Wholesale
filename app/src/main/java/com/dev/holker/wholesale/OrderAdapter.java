package com.dev.holker.wholesale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<OrderItem> {

    public Context mContext;
    int mResource;
    ArrayList<OrderItem> mObjects;


    public OrderAdapter(Context context, int resource, ArrayList<OrderItem> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;
    }


    @NotNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_order, null);

        TextView textViewProductName = view.findViewById(R.id.tv_product_name);
        TextView textViewProductAmount = view.findViewById(R.id.tv_amount_product);

        OrderItem orderItem = mObjects.get(position);

        textViewProductName.setText(orderItem.getProductName());
        textViewProductAmount.setText(orderItem.getProductAmount());

        return view;

    }
}
