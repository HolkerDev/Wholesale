package com.dev.holker.wholesale

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

import kotlinx.android.synthetic.main.activity_list.*

class List : AppCompatActivity() {

    var mOrders = arrayListOf<OrderItem>()
    lateinit var mAdapter: OrderAdapter

    fun toast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_LONG).show()
    }


    //intent to login screen
    private fun goToLogin() {
        val i = Intent(applicationContext, Login::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }


    //check if user logged
    private fun checkUser() {
        if (ParseUser.getCurrentSessionToken() == null) {
            goToLogin()
        }
    }

    //update list of order
    private fun updateOrders() {
        mOrders.clear()
        val query = ParseQuery<ParseObject>("Orders")
        if (ParseUser.getCurrentUser() != null) {
            query.whereEqualTo("username", ParseUser.getCurrentUser().username.toString())
            query.orderByDescending("createdAt")
            query.findInBackground { objects, e ->
                run {
                    if (objects.size > 0) {
                        if (e == null) {
                            for (obj: ParseObject in objects) {
                                mOrders.add(OrderItem(obj.get("name").toString(), obj.get("amount").toString()))
                            }
                            lv_test.adapter = mAdapter
                        } else {
                            toast(e.message.toString())
                        }
                    } else {
                        toast("Objects null")
                    }
                }
            }
        }
    }

    private fun goToSettings() {
        val i = Intent(applicationContext, Settings::class.java)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        checkUser()
        updateOrders()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        checkUser()
        mAdapter = OrderAdapter(applicationContext, R.layout.item_order, mOrders)


        fab.setOnClickListener {
            val intent = Intent(applicationContext, Order::class.java)
            startActivity(intent)
        }


        iv_settings.setOnClickListener {
            goToSettings()
        }

    }

}
