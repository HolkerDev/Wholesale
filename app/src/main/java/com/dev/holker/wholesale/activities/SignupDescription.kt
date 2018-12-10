package com.dev.holker.wholesale.activities

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dev.holker.wholesale.R
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_signup_description.*

class SignupDescription : AppCompatActivity() {

    lateinit var username: String
    lateinit var password: String
    lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_description)

        userType = intent.getStringExtra("type ")
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

        user.signUpInBackground {
            if (it == null) {
                val query = ParseQuery<ParseRole>("_Role")
                query.whereEqualTo("name", userType)
                val role = query.first
                role.users.add(user)
                role.save()
                user.put("role", role)
                user.save()
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
