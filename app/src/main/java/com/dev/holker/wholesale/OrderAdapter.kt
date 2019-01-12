package com.dev.holker.wholesale

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.dev.holker.wholesale.activities.OrderDescription
import com.dev.holker.wholesale.model.OrderItem
import java.util.*

class OrderAdapter(
    private val mContext: Context,
    private val mResource: Int,
    private val mObjects: ArrayList<OrderItem>
) : ArrayAdapter<OrderItem>(mContext, mResource, mObjects) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.item_order_client, null)

        val textViewProductName = view.findViewById<TextView>(R.id.tv_i_order_client_name)
        val textViewNumber = view.findViewById<TextView>(R.id.tv_i_order_client_number)
        val textViewProductAmount = view.findViewById<TextView>(R.id.tv_i_order_client_descr)


        val orderItem = mObjects[position]

        textViewNumber.text = orderItem.number
        textViewProductName.text = orderItem.name
        textViewProductAmount.text = orderItem.amount

        textViewProductName.setOnClickListener {
            val i = Intent(mContext, OrderDescription::class.java)
            i.putExtra("id", orderItem.id)
            mContext.startActivity(i)
        }

        return view

    }
}
