package com.dev.holker.wholesale.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dev.holker.wholesale.OfferAdapterSupplier
import com.dev.holker.wholesale.OrderAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.activities.Order
import com.dev.holker.wholesale.model.OfferItem
import com.dev.holker.wholesale.model.OrderItem
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : androidx.fragment.app.Fragment() {

    //TODO: Fix error connected with async listview


    val mOrders = arrayListOf<OrderItem>()
    val mOffers = arrayListOf<OfferItem>()
    lateinit var mAdapter: ArrayAdapter<OrderItem>
    lateinit var mAdapterOffers: ArrayAdapter<OfferItem>

    private fun toast(string: String?) {
        Toast.makeText(activity!!.applicationContext, string, Toast.LENGTH_SHORT).show()
    }


    private fun updateOrders() {
        mOrders.clear()
        mOffers.clear()
        if (ParseUser.getCurrentUser() != null) {
            val queryRole = ParseQuery<ParseRole>("_Role")
            queryRole.whereEqualTo("users", ParseUser.getCurrentUser())
            val role = queryRole.first

            if (role.getNumber("roleId") == 2) {
                Log.i("MyLog", "it's a client")
                showForClient()
            } else if (role.getNumber("roleId") == 3) {
                Log.i("MyLog", "it's a supplier")
                add_order.visibility = View.INVISIBLE
                showForSupplier()
            } else {
                //if Admin
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

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_orders, null)
    }

    //Shows orders that user created
    fun showForClient() {
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
                                    obj.get("description").toString(),
                                    obj.get("status").toString()
                                )
                            )
                            number++
                        }
                        mAdapter = OrderAdapter(
                            activity!!.applicationContext,
                            R.layout.item_order_client,
                            mOrders
                        )
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

    fun showForSupplierOld() {
        val query = ParseQuery<ParseObject>("OrderOffer")
        query.whereEqualTo("user", ParseUser.getCurrentUser())
        query.findInBackground { objects, e ->
            if (e != null) {
                toast("Error: check logs")
                Log.i("MyLog", e.message)
            } else {
                if (objects.size > 0) {
                    //for counting the order of orders
                    var number = 1

                    var objectId = arrayListOf("nothing")
                    for (objOffer in objects) {
                        val statusCurrent = objOffer.get("status").toString()
                        val objOrder = objOffer.getParseObject("order")

                        //remove repeatings
                        if (objOrder != null && !objectId.contains(objOrder.objectId)) {
                            mOrders.add(
                                OrderItem(
                                    objOrder.objectId,
                                    number.toString(),
                                    objOrder.fetchIfNeeded<ParseObject>().getString("name"),
                                    objOrder.getInt("amount").toString(),
                                    objOrder.get("description").toString(),
                                    statusCurrent
                                    //objOrder.get("status").toString()
                                )
                            )
                            objectId.add(objOrder.objectId)
                            number++
                        }

                        mAdapter = OrderAdapter(
                            activity!!.applicationContext,
                            R.layout.item_order_client,
                            mOrders
                        )

                        lv_orders.adapter = mAdapter
                    }
                } else {
                    toast("You have no offers! Try to add one!")
                }
            }
        }
    }

    fun showForSupplier() {
        val query = ParseQuery<ParseObject>("OrderOffer")
        query.whereEqualTo("user", ParseUser.getCurrentUser())
        query.orderByAscending("createdAt")
        query.findInBackground { objects, e ->
            if (e != null) {
                Log.i("OrdersFragment", e.message)
            } else {
                if (objects.size < 1) {
                    toast("You don't have a offers. Just add one!")
                } else {
                    for (objOffer in objects) {
                        val orderObject = objOffer.getParseObject("order")
                        if (orderObject != null) {
                            mOffers.add(
                                OfferItem(
                                    objOffer.objectId,
                                    null,
                                    orderObject.fetchIfNeeded<ParseObject>().getString("name"),
                                    objOffer.getString("status"),
                                    objOffer.getString("price")
                                )
                            )

                            mAdapterOffers = OfferAdapterSupplier(
                                activity!!.applicationContext,
                                R.layout.item_offer_supplier,
                                mOffers
                            )

                            lv_orders.adapter = mAdapterOffers
                        }
                    }
                }
            }
        }
    }
}