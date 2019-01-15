package com.dev.holker.wholesale.presenters

import android.view.View
import android.widget.Toast
import com.parse.ParseObject

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

    fun getAdapter() {

    }
}