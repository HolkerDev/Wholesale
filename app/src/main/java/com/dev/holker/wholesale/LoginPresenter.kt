package com.dev.holker.wholesale

import android.content.Intent
import android.view.View
import com.dev.holker.wholesale.activities.MainActivity
import com.dev.holker.wholesale.activities.SignUp

class LoginPresenter(val view: View) : ILoginView {
    override fun goToSignUp() {
        val intent = Intent(view.context, SignUp::class.java)
        view.context.startActivity(intent)
    }

    override fun logIn(username: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun goToMain() {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        view.context.startActivity(intent)
    }

    override fun toast() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}