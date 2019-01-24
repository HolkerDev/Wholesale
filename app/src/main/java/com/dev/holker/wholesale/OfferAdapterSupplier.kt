package com.dev.holker.wholesale

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.dev.holker.wholesale.activities.OrderDescription
import com.dev.holker.wholesale.model.OfferItem
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.item_offer_supplier.view.*
import java.util.*

class OfferAdapterSupplier(
    private val mContext: Context,
    private val mResource: Int,
    private val mObjects: ArrayList<OfferItem>
) : ArrayAdapter<OfferItem>(mContext, mResource, mObjects) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.item_offer_supplier, null)


        val offerItem = mObjects[position]

        val queryOffer = ParseQuery<ParseObject>("OrderOffer")
        queryOffer.whereEqualTo("objectId", offerItem.id)
        val offerObj = queryOffer.first
        val orderObj = offerObj.getParseObject("order")

        when (offerItem.status) {
            "In progress" -> {
                view.offer_item_sup_status.background =
                        ColorDrawable(ContextCompat.getColor(mContext, R.color.colorBlue))
            }
            "Accepted" -> {
                view.offer_item_sup_status.background =
                        ColorDrawable(ContextCompat.getColor(mContext, R.color.colorGreen))
            }
            "Declined" -> {
                view.offer_item_sup_status.background =
                        ColorDrawable(ContextCompat.getColor(mContext, R.color.colorRed))
            }
            else -> {
                view.offer_item_sup_status.background =
                        ColorDrawable(ContextCompat.getColor(mContext, R.color.colorBlue))
            }
        }

        view.offer_item_sup_name.text = offerItem.name
        view.offer_item_sup_price.text = offerItem.price

        view.go_to_order.setOnClickListener {

            if (orderObj != null) {
                val intent = Intent(mContext, OrderDescription::class.java)
                intent.putExtra("id", orderObj.objectId)
                mContext.startActivity(intent)
            }
        }

        return view

    }
}
