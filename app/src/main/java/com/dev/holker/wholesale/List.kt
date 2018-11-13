package com.dev.holker.wholesale

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

import kotlinx.android.synthetic.main.activity_list.*

class List : AppCompatActivity() {

    var mOrders = arrayListOf<String>()
    lateinit var mAdapter: ArrayAdapter<String>

    fun toast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_LONG).show()
    }

    private fun goToLogin() {
        val i = Intent(applicationContext, Login::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun checkUser() {
        if (ParseUser.getCurrentUser() == null) {
            goToLogin()
        }
    }

    private fun updateOrders() {
        mOrders.clear()
        val query = ParseQuery<ParseObject>("Orders")
        query.whereEqualTo("username", ParseUser.getCurrentUser().username.toString())
        query.orderByDescending("createdAt")
        query.findInBackground { objects, e ->
            run {
                if (objects.size > 0) {
                    if (e == null) {
                        for (obj: ParseObject in objects) {
                            mOrders.add(obj.get("name").toString())
                        }
                        lv_orders.adapter = mAdapter
                    } else {
                        toast(e.message.toString())
                    }
                } else {
                    toast("Objects null")
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        updateOrders()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        mAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, mOrders)

        checkUser()

        fab.setOnClickListener {
            val intent = Intent(applicationContext, Order::class.java)
            startActivity(intent)
        }

        btn_logOut.setOnClickListener {
            ParseUser.logOut()
            goToLogin()
        }
    }

}
