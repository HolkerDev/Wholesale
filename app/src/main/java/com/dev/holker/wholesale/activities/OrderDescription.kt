package com.dev.holker.wholesale.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.OfferAdapter
import com.dev.holker.wholesale.OfferAdapterMain
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.OfferItem
import com.dev.holker.wholesale.model.RateDialog
import com.dev.holker.wholesale.presenters.OrderDescriptionPresenter
import com.parse.*
import kotlinx.android.synthetic.main.activity_order_description.*

class OrderDescription : AppCompatActivity() {

    //TODO: Update list after adding new offer

    val mOffers = arrayListOf<OfferItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_description)

        val query = ParseQuery<ParseObject>("Order")
        query.whereEqualTo("objectId", intent.getStringExtra("id"))
        val order = query.first

        //Checks the role
        if (ParseUser.getCurrentUser() != null) {
            val queryRole = ParseQuery<ParseRole>("_Role")
            queryRole.whereEqualTo("users", ParseUser.getCurrentUser())
            val role = queryRole.first

            if (role.getNumber("roleId") == 2) {
                Log.i("OrderDescription", "it's a client")
                add_offer.visibility = View.INVISIBLE
                if (order.getParseUser("user")!!.objectId == ParseUser.getCurrentUser().objectId) {
                    if (order.getString("status").equals("Finished") && !order.getBoolean("ratedClient")) {
                        add_rating.visibility = View.VISIBLE
                    }
                }
            } else if (role.getNumber("roleId") == 3) {
                Log.i("OrderDescription", "it's a supplier")
                val status = order.getString("status")
                if (status == "Finished") {
                    add_offer.visibility = View.INVISIBLE
                    val offerQuery = ParseQuery<ParseObject>("OrderOffer")
                    offerQuery.whereEqualTo("order", order)
                    offerQuery.whereEqualTo("status", "Accepted")
                    val offerObj = offerQuery.first
                    Log.i("MyLog", offerObj.getParseUser("user")!!.objectId + " Herer")
                    if (ParseUser.getCurrentUser().objectId == offerObj.getParseUser("user")!!.objectId && !order.getBoolean(
                            "ratedSupplier"
                        )
                    ) {
                        add_rating.visibility = View.VISIBLE
                    }
                }
            } else {
                //if Admin
            }
        }

        val presenter = OrderDescriptionPresenter(findViewById(android.R.id.content))

        //TODO: Move this code to presenter
        //download photo and then attach it to imageview


        val photo = order.get("photo") as ParseFile

        photo.getDataInBackground { data, e ->
            if (e != null) {
                Log.i("MyLog", e.message.toString())
            } else {
                val photoBit = BitmapFactory.decodeByteArray(data, 0, data.size)
                img_order_descr.setImageBitmap(photoBit)
            }
        }

        //set name of order
        tv_name_order_descr.text = presenter.getName(order)
        //set description of order
        tv_descr_order_descr.text = presenter.getDescription(order)

        //Download offers
        val queryOffer = ParseQuery<ParseObject>("OrderOffer")
        queryOffer.whereEqualTo("order", order)
        queryOffer.findInBackground { objects, e ->
            if (e != null) {
                Log.i("MyLog", e.message)
            } else {
                if (objects.size > 0) {
                    for (obj in objects) {
                        Log.i("MyLog", obj.objectId)
                        val sup = obj.getParseUser("user")
                        if (sup != null) {
                            Log.i("MyLog", sup.fetchIfNeeded().username)
                            Log.i("MyLog", obj.objectId)
                            val photoOffer = sup.get("avatar") as ParseFile

                            photoOffer.getDataInBackground { data, error ->
                                if (error != null) {
                                    Log.i("MyLog", error.message.toString())
                                } else {
                                    mOffers.add(
                                        OfferItem(
                                            obj.objectId,
                                            BitmapFactory.decodeByteArray(data, 0, data.size),
                                            sup.getString("name"),
                                            obj.getString("status"),
                                            obj.getString("price")
                                        )
                                    )
                                    Log.i("MyLog", "Down finish")
//                                    mAdapter = OfferAdapter(
//                                        applicationContext,
//                                        R.layout.item_offer,
//                                        mOffers
//                                    )
                                    if (order.getParseUser("user")!!.objectId == ParseUser.getCurrentUser().objectId) {
                                        Log.i("OrderDescription", "owner!")

                                        val status = order.getString("status")
                                        Log.i("OrderDescription", status)
                                        if (status == "Finished") {
                                            val mAdapter = OfferAdapter(
                                                applicationContext,
                                                R.layout.item_offer,
                                                mOffers
                                            )
                                            rv_offers_order_descr.adapter = mAdapter
                                        } else {
                                            val mAdapter = OfferAdapterMain(
                                                applicationContext,
                                                R.layout.item_offer_client,
                                                mOffers
                                            )
                                            rv_offers_order_descr.adapter = mAdapter
                                        }
                                    } else {
                                        Log.i("OrderDescription", "just user")
                                        val mAdapter = OfferAdapter(
                                            applicationContext,
                                            R.layout.item_offer,
                                            mOffers
                                        )
                                        rv_offers_order_descr.adapter = mAdapter
                                    }

                                }
                            }
                        }
                    }

                }
            }
        }

        add_offer.setOnClickListener {
            val i = Intent(this, Offer::class.java)
            i.putExtra("order", order.objectId)
            startActivity(i)
        }

        add_rating.setOnClickListener {
            val dialog = RateDialog()
            val fm = supportFragmentManager

            val queryRole = ParseQuery<ParseRole>("_Role")
            queryRole.whereEqualTo("users", ParseUser.getCurrentUser())
            val role = queryRole.first

            dialog.client = role.getNumber("roleId") == 2
            Log.i("MyLog", "Order id before ${order.objectId}")
            dialog.orderId = order.objectId

            dialog.show(fm, "Rating")
        }
    }
}
