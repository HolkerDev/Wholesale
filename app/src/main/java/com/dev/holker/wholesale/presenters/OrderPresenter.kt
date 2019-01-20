package com.dev.holker.wholesale.presenters

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
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

        obj.saveInBackground {
            if (it != null) {
                toast(it.message.toString())
            } else {
                toast("Order is ready!")
                val activity = scanForActivity(view.context)
                if (activity != null) {
                    activity.finish()
                }
            }
        }
    }

    private fun scanForActivity(cont: Context?): Activity? {
        if (cont == null)
            return null
        else if (cont is Activity)
            return cont
        else if (cont is ContextWrapper)
            return scanForActivity((cont as ContextWrapper).baseContext)

        return null
    }
}