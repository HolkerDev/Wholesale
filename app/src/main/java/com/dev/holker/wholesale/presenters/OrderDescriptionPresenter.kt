package com.dev.holker.wholesale.presenters

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseUser

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

    fun canRateSupplier(order:ParseObject): Boolean {
        return ParseUser.getCurrentUser().objectId == acceptedOffer(order).getParseUser("user")!!.objectId && !order.getBoolean(
            "ratedSupplier")
    }
}