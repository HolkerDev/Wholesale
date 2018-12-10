package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.presenters.SignUpPresenter
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val presenter = SignUpPresenter(findViewById(android.R.id.content))
        spinner.adapter = presenter.getAdapter()

        btn_next.setOnClickListener {
            presenter.goToNext(
                et_sign_username.text.toString(),
                et_sign_password_confirm.toString(),
                spinner.selectedItem.toString()
            )
        }
    }
}
