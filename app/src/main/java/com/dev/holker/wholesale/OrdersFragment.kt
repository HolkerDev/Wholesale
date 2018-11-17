package com.dev.holker.wholesale

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : Fragment() {

    val mOrders = arrayListOf<OrderItem>()
    lateinit var mAdapter: ArrayAdapter<OrderItem>


    private fun toast(string: String?) {
        Toast.makeText(activity!!.applicationContext, string, Toast.LENGTH_SHORT).show()
    }


    private fun updateOrders() {
        mOrders.clear()
        val query = ParseQuery<ParseObject>("Orders")
        if (ParseUser.getCurrentUser() != null) {
            query.whereEqualTo("username", ParseUser.getCurrentUser().username.toString())
            query.orderByDescending("createdAt")
            query.findInBackground { objects, e ->
                run {
                    if (objects.size > 0) {
                        if (e == null) {
                            for (obj: ParseObject in objects) {
                                mOrders.add(OrderItem(obj.get("name").toString(), obj.get("amount").toString()))
                            }
                            lv_orders.adapter = mAdapter
                        } else {
                            toast(e.message.toString())
                        }
                    } else {
                        toast("Objects null")
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        updateOrders()

        add_order.setOnClickListener {
            val i = Intent(activity!!.applicationContext, Order::class.java)
            startActivity(i)
        }

        lv_orders.adapter = mAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = OrderAdapter(activity!!.applicationContext, R.layout.item_order, mOrders)
        return inflater.inflate(R.layout.fragment_orders, null)
    }
}