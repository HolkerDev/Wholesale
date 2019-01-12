package com.dev.holker.wholesale.presenters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Toast
import com.dev.holker.wholesale.presenters.interfaces.IOrderDescriptionPresenter
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery

class OrderDescriptionPresenter(val view: View) : IOrderDescriptionPresenter {
    lateinit var bitmap: Bitmap
    override fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }

    override fun downloadPhoto(id: String): Bitmap? {
        val query = ParseQuery<ParseObject>("Order")
        query.whereEqualTo("objectId", id)
        val order = query.first
        val photo = order.get("photo")
    }
}