package com.dev.holker.wholesale.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dev.holker.wholesale.R
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_order.*

class Order : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        fun toast(string: String) {
            Toast.makeText(applicationContext, string, Toast.LENGTH_LONG).show()
        }

        btn_submit.setOnClickListener { view ->
            run {
                val obj = ParseObject("Orders")
                obj.put("name", et_productName.text.toString())
                obj.put("amount", et_amount.text.toString())
                obj.put("username", ParseUser.getCurrentUser().username)
                obj.put("description", et_description.text.toString())
                obj.saveInBackground {
                    if (it == null) {
                        toast("Fine!")
                        finish()
                    } else {
                        toast(it.message.toString())
                        finish()
                    }
                }
            }
        }
    }
}
