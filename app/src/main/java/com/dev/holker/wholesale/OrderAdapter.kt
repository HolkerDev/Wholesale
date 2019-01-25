package com.dev.holker.wholesale

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dev.holker.wholesale.activities.OrderDescription
import com.dev.holker.wholesale.model.OrderItem
import kotlinx.android.synthetic.main.item_order_client.view.*
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
        val textViewProductAmount = view.findViewById<TextView>(R.id.tv_i_order_client_descr)


        val orderItem = mObjects[position]

        when (orderItem.status) {
            "In progress" -> {
                view.view_order_status.background = ColorDrawable(ContextCompat.getColor(mContext, R.color.colorBlue))
                view.view_order_status.text = "In progress"
            }
            "Finished" -> {
                view.view_order_status.background = ColorDrawable(ContextCompat.getColor(mContext, R.color.colorGreen))
                view.view_order_status.text = "Finished"
            }
            else -> {
                view.view_order_status.background = ColorDrawable(ContextCompat.getColor(mContext, R.color.colorBlue))
                view.view_order_status.text = "In progress"
            }
        }


        textViewProductName.text = orderItem.name
        textViewProductAmount.text = orderItem.amount + " pc"

        textViewProductName.setOnClickListener {
            val i = Intent(mContext, OrderDescription::class.java)
            i.putExtra("id", orderItem.id)
            mContext.startActivity(i)
        }

        return view

    }
}
