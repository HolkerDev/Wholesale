package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.Location
import com.dev.holker.wholesale.model.LocationDialog
import com.dev.holker.wholesale.model.LocationDialogOffer
import com.dev.holker.wholesale.model.LocationShort
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_offer.*

class Offer : AppCompatActivity(), LocationDialogOffer.NoticeDialogListenerShort {
    lateinit var location: LocationShort

    override fun apply(country: String, city: String) {
        this.location = LocationShort(country, city)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)
        Log.i("OfferActivity", intent.getStringExtra("order"))



        offer_add_location.setOnClickListener {
            getLocation()
        }

        offer_submit.setOnClickListener {
            //find order
            val queryOrder = ParseQuery<ParseObject>("Order")
            queryOrder.whereEqualTo("objectId", intent.getStringExtra("order"))
            val order = queryOrder.first
            //create new row in table
            val offer = ParseObject("OrderOffer")
            //put link to current user
            offer.put("user", ParseUser.getCurrentUser())
            //put link to current order
            offer.put("order", order)
            offer.put("price", offer_price.text.toString())
            offer.put("status", "In progress")
            offer.put("comment", offer_comment.text.toString())


            offer.saveInBackground {
                if (it != null) {
                    Log.i("OfferActivity", "Problem with saving")
                } else {
                    Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    fun getLocation() {
        val dialog = LocationDialog()
        val fm = supportFragmentManager
        dialog.show(fm, "location")
    }

}
