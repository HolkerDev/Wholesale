package com.dev.holker.wholesale

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : Fragment() {

    val orders = arrayListOf<String>()

    override fun onStart() {
        super.onStart()
        orders.add("First one")
        orders.add("Second one")
        orders.add("third one")

        val ordersAdapter = ArrayAdapter<String>(activity!!.applicationContext, android.R.layout.simple_list_item_1, orders)

        lv_orders.adapter = ordersAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orders, null)
    }
}