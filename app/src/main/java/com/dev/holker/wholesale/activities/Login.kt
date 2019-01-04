package com.dev.holker.wholesale.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.presenters.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Create presenter
        val presenter = LoginPresenter(findViewById(android.R.id.content))

        //Button Log In
        btn_login.setOnClickListener {
            presenter.logIn(et_username.text.toString(), et_password.text.toString())
        }

        //Button 'Don't have an account'
        tv_signup.setOnClickListener {
            presenter.goToSignUp()
        }
    }
}
