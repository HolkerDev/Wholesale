package com.dev.holker.wholesale.model

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import java.util.*

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


        fun toast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun sortDates(messages: ArrayList<MessageItem>) {
            val n = messages.size
            var temp: MessageItem
            for (i in 0 until n) {
                for (j in 1 until n - i) {
                    if (messages[j - 1].date!!.after(messages[j].date)) {
                        //swap elements
                        temp = messages[j - 1]
                        messages[j - 1] = messages[j]
                        messages[j] = temp
                    }

                }
            }
        }
    }
}