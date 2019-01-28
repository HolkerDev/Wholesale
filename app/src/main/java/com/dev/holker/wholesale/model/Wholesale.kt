package com.dev.holker.wholesale.model

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast

class Wholesale {
    companion object {

        fun scanForActivity(cont: Context?): Activity? {
            if (cont == null)
                return null
            else if (cont is Activity)
                return cont
            else if (cont is ContextWrapper)
                return scanForActivity((cont as ContextWrapper).baseContext)

            return null
        }
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}