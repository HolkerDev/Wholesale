package com.dev.holker.wholesale.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.widget.Toast
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.R.id.profile_image
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseSession
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_signup_description.*

class SignupDescription : AppCompatActivity() {

    lateinit var username: String
    lateinit var password: String
    lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_description)

        val i = intent
        userType = intent.getStringExtra("typeOfUser")
        username = intent.getStringExtra("username")
        password = intent.getStringExtra("password")

        //change hint of 'company name'
        when (userType) {
            "Client" -> {
                et_signup_descr_name.hint = "Your name"
            }
            "Supplier" -> {
                et_signup_descr_name.hint = "Name of your company"
            }
        }
        btn_attach.setOnClickListener {
            getPhoto()
        }

        btn_signup.setOnClickListener {
            signUp()
        }
    }


    fun toast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }


    //sign up new user
    private fun signUp() {
        val user = ParseUser()
        user.username = username
        user.setPassword(password)

        if (userType == "Client") {
            user.put("orderId", "Ddz31dBK1e")
        } else {
            user.put("orderId", "ApHm7d1h5J")
        }
        user.signUpInBackground {
            if (it == null) {
                val query = ParseQuery<ParseRole>("_Role")
                query.whereEqualTo("name", userType)
                val role = query.first
                role.users.add(user)
                role.save()
                toast("Fine!")
            } else {
                toast(it.message.toString())
            }
        }
    }

    //open gallery and user can select the photo
    private fun getPhoto() {
        val photoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoIntent, 1);
    }


    //get selected photo if its was selected
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val image = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, image)
            profile_image.setImageBitmap(bitmap)
        }

    }

}
