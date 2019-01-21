package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_offer.*

class Offer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)
        Log.i("OfferActivity", intent.getStringExtra("order"))

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
}
