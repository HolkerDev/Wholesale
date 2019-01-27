package com.dev.holker.wholesale

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.dev.holker.wholesale.model.OfferItem
import kotlinx.android.synthetic.main.item_offer.view.*
import kotlinx.android.synthetic.main.item_offer_supplier.view.*
import java.util.*

class OfferAdapter(
    private val mContext: Context,
    private val mResource: Int,
    private val mObjects: ArrayList<OfferItem>
) : ArrayAdapter<OfferItem>(mContext, mResource, mObjects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.item_offer, null)

        val offer = mObjects[position]


        when (offer.status) {

            "In progress" -> {
                view.offer_item_status.background =
                    ColorDrawable(ContextCompat.getColor(mContext, R.color.colorBlue))

            }
            "Accepted" -> {
                view.offer_item_status.background =
                    ColorDrawable(ContextCompat.getColor(mContext, R.color.colorGreen))

            }
            "Declined" -> {
                view.offer_item_status.background =
                    ColorDrawable(ContextCompat.getColor(mContext, R.color.colorRed))

            }
            else -> {
                view.offer_item_status.background =
                    ColorDrawable(ContextCompat.getColor(mContext, R.color.colorBlue))
                view.offer_item_sup_status.text = "Unknown"
            }
        }

        view.layout_offer.setOnClickListener {
            val intent = Intent(mContext, OfferDescriptionActivity::class.java)
            intent.putExtra("id", offer.id)
            mContext.startActivity(intent)
        }


        view.offer_item_avatar.setImageBitmap(offer.avatar)
        view.offer_item_name.text = offer.name
        view.offer_item_price.text = offer.price + "$"
        view.offer_item_status.text = offer.status

        return view

    }
}