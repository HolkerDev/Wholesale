package com.dev.holker.wholesale.subfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dev.holker.wholesale.OrderAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.OrderItem
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.subfragment_orders.*

class OrdersSubfragment : Fragment() {


    val mOrders = arrayListOf<OrderItem>()
    lateinit var mAdapter: ArrayAdapter<OrderItem>
    private final val TAG = OrdersSubfragment::class.java.name


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mAdapter = OrderAdapter(
            activity!!.applicationContext,
            R.layout.item_order_client,
            mOrders
        )
        mOrders.clear()

        showAllOrders()
        val view = inflater.inflate(R.layout.subfragment_orders, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    fun showAllOrders() {
        mOrders.clear()
        val query = ParseQuery<ParseObject>("Order")

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
                                    obj.get("description").toString(),
                                    obj.get("status").toString()
                                )
                            )
                            number++
                        }
                        lv_orders_all.adapter = mAdapter
                    } else {
                        Log.i(TAG, e.message)
                    }
                } else {
                    Toast.makeText(context, "List of orders is empty!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}