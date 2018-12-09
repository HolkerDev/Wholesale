package com.dev.holker.wholesale.presenters

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.dev.holker.wholesale.activities.MainActivity
import com.dev.holker.wholesale.activities.SignUp
import com.parse.ParseUser

class LoginPresenter(val view: View) : ILoginPresenter {
    override fun goToSignUp() {
        val intent = Intent(view.context, SignUp::class.java)
        view.context.startActivity(intent)
    }

    override fun logIn(username: String, password: String) {
        ParseUser.logInInBackground(username, password) { user, e ->
            run {
                if (e != null) {
                    toast(e.message.toString())
                } else {
                    toast("Logged In")
                    goToMain()
                }
            }
        }
    }

    override fun goToMain() {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        view.context.startActivity(intent)
    }

    override fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_LONG).show()
    }
}