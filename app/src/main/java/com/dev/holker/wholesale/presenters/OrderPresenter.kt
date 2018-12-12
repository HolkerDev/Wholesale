package com.dev.holker.wholesale.presenters

import android.app.Activity
import android.view.View
import android.widget.Toast
import com.dev.holker.wholesale.presenters.interfaces.IOrderPresenter
import com.parse.ParseObject

class OrderPresenter(val view: View) : IOrderPresenter {
    override fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }

    override fun submit(name: String, amount: Int, descr: String) {
        val obj = ParseObject("Order")
        obj.put("name", name)
        obj.put("amount", amount)
        obj.put("description", descr)
        obj.saveInBackground {
            if (it != null) {
                toast(it.message.toString())
            } else {
                toast("Order is ready!")
                val activity = view.context as Activity
                activity.finish()
            }
        }
    }
}