package com.dev.holker.wholesale.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.dev.holker.wholesale.R
import kotlinx.android.synthetic.main.activity_signup_description.*

class SignupDescription : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_description)

        val i = intent
        val userType = intent.getStringExtra("typeOfUser")

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
            //            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
//            } else {
//                getPhoto()
//            }
            getPhoto()
        }
    }

    fun getPhoto() {
        val photoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoIntent, 1);
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val image = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, image)
            profile_image.setImageBitmap(bitmap)
        }

    }

}
