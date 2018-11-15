package com.dev.holker.wholesale

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    //intent to login screen
    private fun goToLogin() {
        val i = Intent(applicationContext, Login::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btn_logOut.setOnClickListener {
            ParseUser.logOut()
            goToLogin()
        }
    }
}
