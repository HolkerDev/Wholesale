package com.dev.holker.wholesale.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.OfferAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.Offer
import com.dev.holker.wholesale.presenters.OrderDescriptionPresenter
import com.parse.*
import kotlinx.android.synthetic.main.activity_order_description.*

class OrderDescription : AppCompatActivity() {

    lateinit var mAdapter: OfferAdapter
    val mOffers = arrayListOf<Offer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_description)


        //Checks the role
        if (ParseUser.getCurrentUser() != null) {
            val queryRole = ParseQuery<ParseRole>("_Role")
            queryRole.whereEqualTo("users", ParseUser.getCurrentUser())
            val role = queryRole.first

            if (role.getNumber("roleId") == 2) {
                Log.i("OrderDescription", "it's a client")
                add_offer.visibility = View.INVISIBLE
            } else if (role.getNumber("roleId") == 3) {
                Log.i("OrderDescription", "it's a supplier")
            } else {
                //if Admin
            }
        }

        val presenter = OrderDescriptionPresenter(findViewById(android.R.id.content))

        //TODO: Move this code to presenter
        //download photo and then attach it to imageview
        val query = ParseQuery<ParseObject>("Order")
        query.whereEqualTo("objectId", intent.getStringExtra("id"))
        val order = query.first
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

                            photoOffer.getDataInBackground { data, e ->
                                if (e != null) {
                                    Log.i("MyLog", e.message.toString())
                                } else {
                                    mOffers.add(
                                        Offer(
                                            BitmapFactory.decodeByteArray(data, 0, data.size),
                                            sup.username,
                                            obj.getString("status"),
                                            obj.getString("price")
                                        )
                                    )
                                    Log.i("MyLog", "Down finish")
                                    mAdapter = OfferAdapter(
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

        mAdapter = OfferAdapter(
            applicationContext,
            R.layout.item_offer,
            mOffers
        )

        rv_offers_order_descr.adapter = mAdapter

    }

    override fun onResume() {
        rv_offers_order_descr.adapter = mAdapter
        super.onResume()
    }
}
