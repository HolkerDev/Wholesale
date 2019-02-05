package com.dev.holker.wholesale.presenters

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.dev.holker.wholesale.OfferAdapter
import com.dev.holker.wholesale.OfferAdapterMain
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.OfferItem
import com.parse.*

class OrderDescriptionPresenter(val view: View) {

    fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }

    //return name field
    fun getName(obj: ParseObject): String {
        if (obj.getString("name") == null) {
            return "Nothing"
        } else {
            return obj.getString("name")!!
        }
    }

    //returns description field
    fun getDescription(obj: ParseObject): String {
        return obj.getString("description")!!
    }

    fun getOrder(intent: Intent): ParseObject {
        val query = ParseQuery<ParseObject>("Order")
        query.whereEqualTo("objectId", intent.getStringExtra("id"))
        return query.first
    }

    fun isClient(): Boolean {
        val role = getRole()
        return role.getNumber("roleId") == 2
    }

    private fun getRole(): ParseRole {
        val queryRole = ParseQuery<ParseRole>("_Role")
        queryRole.whereEqualTo("users", ParseUser.getCurrentUser())
        return queryRole.first
    }

    //Is this user created order
    fun isOwner(order: ParseObject): Boolean {
        return order.getParseUser("user")!!.objectId == ParseUser.getCurrentUser().objectId
    }

    //Is this order finished
    fun canRateClient(order: ParseObject): Boolean {
        return order.getString("status").equals("Finished") && !order.getBoolean("ratedClient")
    }

    fun isFinished(order: ParseObject): Boolean {
        return order.getString("status") == "Finished"
    }

    private fun acceptedOffer(order: ParseObject): ParseObject {
        val offerQuery = ParseQuery<ParseObject>("OrderOffer")
        offerQuery.whereEqualTo("order", order)
        offerQuery.whereEqualTo("status", "Accepted")
        return offerQuery.first
    }

    fun canRateSupplier(order: ParseObject): Boolean {
        return ParseUser.getCurrentUser().objectId == acceptedOffer(order).getParseUser("user")!!.objectId && !order.getBoolean(
            "ratedSupplier"
        )
    }

    fun downloadOffers(mOffers: ArrayList<OfferItem>, listView: ListView, order: ParseObject) {

        //Download offers and attach them to listView
        val queryOffer = ParseQuery<ParseObject>("OrderOffer")
        queryOffer.whereEqualTo("order", order)
        queryOffer.findInBackground { objects, e ->
            if (e != null) {
                Log.i("OrderDescription", e.message)
            } else {

                if (objects.size > 0) {

                    for (obj in objects) {

                        val supplierUser = obj.getParseUser("user")

                        if (supplierUser != null) {

                            Log.i("OrderDescription", supplierUser.fetchIfNeeded().username)
                            Log.i("OrderDescription", obj.objectId)

                            val photoOffer = supplierUser.get("avatar") as ParseFile

                            photoOffer.getDataInBackground { data, error ->
                                if (error != null) {

                                    Log.i("OrderDescription", error.message.toString())

                                } else {

                                    mOffers.add(
                                        OfferItem(
                                            obj.objectId,
                                            BitmapFactory.decodeByteArray(data, 0, data.size),
                                            supplierUser.getString("name"),
                                            obj.getString("status"),
                                            obj.getString("price")
                                        )
                                    )

                                    Log.i("OrderDescription", "Download is finished")

                                    if (isOwner(order)) {
                                        Log.i("OrderDescription", "Showing list for owner")

                                        if (isFinished(order)) {
                                            attachAdapterDefault(listView,mOffers)
                                        } else {
                                            attachAdapterForClient(listView,mOffers)
                                        }

                                    } else {
                                        Log.i("OrderDescription", "Showing list for default user")

                                        attachAdapterDefault(listView,mOffers)
                                    }

                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private fun attachAdapterForClient(listView:ListView,mOffers: ArrayList<OfferItem>){
        val mAdapter = OfferAdapterMain(
            view.context,
            R.layout.item_offer_client,
            mOffers
        )
        listView.adapter = mAdapter
    }

    private fun attachAdapterDefault(listView:ListView,mOffers: ArrayList<OfferItem>){
        val mAdapter = OfferAdapterMain(
            view.context,
            R.layout.item_offer,
            mOffers
        )
        listView.adapter = mAdapter
    }
}