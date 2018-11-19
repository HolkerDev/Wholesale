package com.dev.holker.wholesale.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dev.holker.wholesale.R
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private fun toast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }


    //SignUp new user
    private fun signUp() {
        val user = ParseUser()
        if (et_password.text.toString().equals(et_sign_password_confirm.text.toString())) {
            user.username = et_sign_username.text.toString()
            user.setPassword(et_sign_password_confirm.text.toString())
            user.signUpInBackground {
                if (it == null) {
                    toast("Successful")
                } else {
                    toast(it.message.toString())
                }
            }
        } else {
            toast("Wrong password!")
        }

    }


    private fun goToHome() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        btn_signup.setOnClickListener {
            signUp()
            goToHome()
        }
    }
}