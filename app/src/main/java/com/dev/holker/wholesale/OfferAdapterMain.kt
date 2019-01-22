package com.dev.holker.wholesale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dev.holker.wholesale.model.OfferItem
import kotlinx.android.synthetic.main.item_offer.view.*
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

        view.order_item_avatar.setImageBitmap(offer.avatar)
        view.order_item_name.text = offer.name
        view.order_item_price.text = offer.price

        return view

    }
}