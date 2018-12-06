package com.dev.holker.wholesale.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dev.holker.wholesale.R
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private fun toast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }


    private fun goToHome() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val role = arrayListOf("Client", "Supplier")

        val arrayAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, role)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        btn_next.setOnClickListener {
            val i = Intent(applicationContext, SignupDescription::class.java)
            i.putExtra("username", et_sign_username.text.toString())
            i.putExtra("password", et_sign_password.text.toString())
            i.putExtra("typeOfUser", spinner.selectedItem.toString())
            startActivity(i)
        }
    }
}
