package com.dev.holker.wholesale.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.OfferItem
import com.dev.holker.wholesale.model.RateDialog
import com.dev.holker.wholesale.presenters.OrderDescriptionPresenter
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_order_description.*

class OrderDescription : AppCompatActivity() {

    //TODO: Update list after adding new offer

    val mOffers = arrayListOf<OfferItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_description)

        val presenter = OrderDescriptionPresenter(findViewById(android.R.id.content))

        val order = presenter.getOrder(intent)

        if (presenter.isClient()) {
            Log.i("OrderDescription", "It's a client")

            //client can't add an offer
            add_offer.visibility = View.INVISIBLE

            if (presenter.isOwner(order) && presenter.canRateClient(order)) {
                //if order is finished and client is owner, we'll give it possibility to rate
                add_rating.visibility = View.VISIBLE
            }

        } else {
            Log.i("OrderDescription", "It's a supplier")

            if (presenter.isFinished(order)) {
                //if order is finished supplier can't add new offer
                add_offer.visibility = View.INVISIBLE

                if (presenter.canRateSupplier(order)) {
                    //check if supplier can rate a client
                    add_rating.visibility = View.VISIBLE
                }
            }
        }


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

        //download offers
        presenter.downloadOffers(mOffers, rv_offers_order_descr, order)

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
