package com.dev.holker.wholesale;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.dev.holker.wholesale.activities.OrderDescription;
import com.dev.holker.wholesale.model.OrderItem;
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

        View view = inflater.inflate(R.layout.item_order_client, null);

        TextView textViewProductName = view.findViewById(R.id.tv_i_order_client_name);
        TextView textViewNumber = view.findViewById(R.id.tv_i_order_client_number);
        TextView textViewProductAmount = view.findViewById(R.id.tv_i_order_client_descr);


        final OrderItem orderItem = mObjects.get(position);

        textViewNumber.setText(orderItem.getNumber());
        textViewProductName.setText(orderItem.getName());
        textViewProductAmount.setText(orderItem.getAmount());

        textViewProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, OrderDescription.class);
                i.putExtra("id", orderItem.getId());
                mContext.startActivity(i);
            }
        });

        return view;

    }
}
