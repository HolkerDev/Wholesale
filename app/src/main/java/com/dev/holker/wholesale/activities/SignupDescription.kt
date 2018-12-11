package com.dev.holker.wholesale.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.presenters.SignUpDescriptionPresenter
import kotlinx.android.synthetic.main.activity_signup_description.*

class SignupDescription : AppCompatActivity() {

    lateinit var username: String
    lateinit var password: String
    lateinit var userType: String
    lateinit var avatar: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_description)

        //set avatar as default image
        avatar = ((profile_image.drawable) as BitmapDrawable).bitmap

        //create presenter
        val presenter = SignUpDescriptionPresenter(findViewById(android.R.id.content))

        //change hint of name pole
        et_signup_descr_name.hint = presenter.getHint(intent)

        btn_attach.setOnClickListener {
            getPhoto()
        }

        btn_signup.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            btn_signup.isActivated = false
            presenter.signUp(intent, avatar)
            btn_signup.isActivated = true
            progressBar.visibility = View.INVISIBLE
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
            avatar = MediaStore.Images.Media.getBitmap(contentResolver, image)
            profile_image.setImageBitmap(avatar)
        }
    }

}
