package com.dev.holker.wholesale

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_offer_description.*

class OfferDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_description)

        val offerId = intent.getStringExtra("id")

        val queryOffer = ParseQuery<ParseObject>("OrderOffer")
        queryOffer.whereEqualTo("objectId", offerId)
        val offer = queryOffer.first

        offer_descr_price.setText(offer.getString("price") + "$")
        offer_descr_comment.setText(offer.getString("comment"))

        val city = offer.getParseObject("supplierLocation")

        var location = city!!.fetchIfNeeded<ParseObject>().getString("name")

        var country = city.getParseObject("country")

        location += ", ${country!!.fetchIfNeeded<ParseObject>().getString("name")}"

        offer_descr_location.setText(location)

        offer_descr_submit.setOnClickListener {
            finish()
        }
    }
}
