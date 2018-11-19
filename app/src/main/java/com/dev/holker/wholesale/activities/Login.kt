package com.dev.holker.wholesale.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dev.holker.wholesale.R
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    //Create toast message
    private fun toast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_LONG).show()
    }


    //LogIn user
    private fun logIn() {
        ParseUser.logInInBackground(
            et_username.text.toString(),
            et_password.text.toString()
        ) { _: ParseUser?, e: ParseException? ->
            run {
                if (e == null) {
                    toast("Successful")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    toast(e.message.toString())
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //login and go to Home Activity
        btn_login.setOnClickListener {
            logIn()
        }

        //open signUp activity
        tv_signup.setOnClickListener {
            val i = Intent(applicationContext, SignUp::class.java)
            startActivity(i)
        }
    }
}
