package com.dev.holker.wholesale.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dev.holker.wholesale.OrderAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.activities.Order
import com.dev.holker.wholesale.model.OrderItem
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : androidx.fragment.app.Fragment() {

    //TODO: Fix error connected with async listview


    val mOrders = arrayListOf<OrderItem>()
    lateinit var mAdapter: ArrayAdapter<OrderItem>

    private fun toast(string: String?) {
        Toast.makeText(activity!!.applicationContext, string, Toast.LENGTH_SHORT).show()
    }


    private fun updateOrders() {
        mOrders.clear()
        if (ParseUser.getCurrentUser() != null) {
            val query = ParseQuery<ParseObject>("Order")
            //query.whereEqualTo("username", ParseUser.getCurrentUser().username.toString())
            query.whereContainedIn("user", listOf(ParseUser.getCurrentUser()))
            query.orderByAscending("createdAt")
            query.findInBackground { objects, e ->
                run {
                    if (objects != null) {
                        if (e == null) {
                            var number = 1
                            for (obj: ParseObject in objects) {
                                mOrders.add(
                                    OrderItem(
                                        obj.objectId,
                                        number.toString(),
                                        obj.get("name").toString(),
                                        obj.getInt("amount").toString(),
                                        obj.get("description").toString()
                                    )
                                )
                                number++
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

        refresh.setOnRefreshListener {
            updateOrders()
            refresh.isRefreshing = false
        }

        add_order.setOnClickListener {
            val i = Intent(activity!!.applicationContext, Order::class.java)
            startActivity(i)
        }

        lv_orders.adapter = mAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = OrderAdapter(
            activity!!.applicationContext,
            R.layout.item_order_client,
            mOrders
        )
        return inflater.inflate(R.layout.fragment_orders, null)
    }
}