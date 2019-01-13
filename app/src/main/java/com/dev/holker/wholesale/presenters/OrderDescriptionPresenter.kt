package com.dev.holker.wholesale.presenters

import android.view.View
import android.widget.Toast

class OrderDescriptionPresenter(val view: View) {
    fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }
}