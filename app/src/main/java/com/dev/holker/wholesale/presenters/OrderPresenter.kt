package com.dev.holker.wholesale.presenters

import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import com.dev.holker.wholesale.model.Wholesale
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import java.io.ByteArrayOutputStream


class OrderPresenter(val view: View) {
    fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }

    fun submit(name: String, amount: Int, descr: String, user: ParseUser, productType: String, photo: Bitmap) {
        val obj = ParseObject("Order")
        obj.put("name", name)
        obj.put("amount", amount)
        obj.put("description", descr)

        val queryType = ParseQuery<ParseObject>("ProductType")
        queryType.whereEqualTo("name", productType)
        val type = queryType.first

        val stream = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes = stream.toByteArray()
        val photoFile = ParseFile(bytes, "photo.png")
        obj.put("photo", photoFile)
        obj.put("user", user)
        obj.put("productType", type)
        obj.put("status", "In progress")
        obj.put("ratedClient", false)
        obj.put("ratedSupplier", false)

        obj.saveInBackground {
            if (it != null) {
                toast(it.message.toString())
            } else {
                toast("Order is ready!")
                val activity = Wholesale.scanForActivity(view.context)
                if (activity != null) {
                    activity.finish()
                }
            }
        }
    }
}