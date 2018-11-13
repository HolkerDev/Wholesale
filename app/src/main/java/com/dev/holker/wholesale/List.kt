package com.dev.holker.wholesale

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.parse.ParseUser

import kotlinx.android.synthetic.main.activity_list.*

class List : AppCompatActivity() {

    fun goToLogin() {
        val i = Intent(applicationContext, Login::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    fun checkUser() {
        if (ParseUser.getCurrentUser() == null) {
            goToLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

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
