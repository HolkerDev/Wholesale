package com.dev.holker.wholesale

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dev.holker.wholesale.activities.MainActivity
import com.dev.holker.wholesale.model.OfferItem
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.item_offer_client.view.*
import java.util.*

class OfferAdapterMain(
    private val mContext: Context,
    private val mResource: Int,
    private val mObjects: ArrayList<OfferItem>
) : ArrayAdapter<OfferItem>(mContext, mResource, mObjects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.item_offer_client, null)

        val offer = mObjects[position]

        view.offer_item_client_avatar.setImageBitmap(offer.avatar)
        view.offer_item_client_name.text = offer.name
        view.offer_item_client_price.text = offer.price + "$"

        view.offer_item_client_accept.setOnClickListener {
            val query = ParseQuery<ParseObject>("OrderOffer")
            query.whereEqualTo("objectId", offer.id)
            val offerObject = query.first
            offerObject.put("status", "Accepted")
            offerObject.saveInBackground {
                val orderObject = offerObject.getParseObject("order")
                if (orderObject != null) {
                    orderObject.put("status", "Finished")
                    orderObject.saveInBackground {
                        val queryOffers = ParseQuery<ParseObject>("OrderOffer")
                        queryOffers.whereEqualTo("order", orderObject)
                        queryOffers.whereNotEqualTo("objectId", offer.id)
                        queryOffers.findInBackground { objects, error ->
                            if (error != null) {
                                Log.i("OfferAdapterMain", error.message)
                            } else {
                                if (objects.size < 1) {
                                    Log.i("OfferAdapterMain", "no objects")
                                } else {
                                    for (oneOffer in objects) {
                                        oneOffer.put("status", "Declined")
                                        oneOffer.save()
                                    }
                                }
                            }
                        }
                        goToMain()
                    }
                }
            }

        }

        view.layout_offer_client.setOnClickListener {
            val intent = Intent(mContext, OfferDescriptionActivity::class.java)
            intent.putExtra("id", offer.id)
            mContext.startActivity(intent)
        }

        return view

    }

    fun goToMain() {
        val i = Intent(mContext, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        mContext.startActivity(i)
    }
}