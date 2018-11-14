package com.dev.holker.wholesale;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<OrderItem> {

    private Context mContext;
    private int mResource;
    private ArrayList<OrderItem> mObjects;


    public OrderAdapter(Context context, int resource, ArrayList<OrderItem> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mResource = resource;
        this.mObjects = objects;
    }


    @NotNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_order, null);

        Button buttonCheck = view.findViewById(R.id.btn_check);
        TextView textViewProductName = view.findViewById(R.id.tv_product_name);
        TextView textViewProductAmount = view.findViewById(R.id.tv_amount_product);

        final OrderItem orderItem = mObjects.get(position);

        textViewProductName.setText(orderItem.getProductName());
        textViewProductAmount.setText(orderItem.getProductAmount());

        buttonCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, OrderDescription.class);
                i.putExtra("order", orderItem.getProductName());

                mContext.startActivity(i);
            }
        });


        return view;

    }
}
