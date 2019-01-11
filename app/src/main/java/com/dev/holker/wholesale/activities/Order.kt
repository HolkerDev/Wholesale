package com.dev.holker.wholesale.activities

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.presenters.OrderPresenter
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_order.*


class Order : AppCompatActivity() {
    var productType = "None"
    lateinit var photo: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        photo = BitmapFactory.decodeResource(resources, R.drawable.unknown)

        btn_select_type.setOnClickListener {
            val dialogOrder: AlertDialog
            val items = arrayOf(
                "Dining Sets", "Outdoor Lounge Chairs", "Banquet Chairs", "Tables",
                "High chairs", "Bar Stools"
            )
            val builder = AlertDialog.Builder(this@Order)
            builder.setTitle("Select background")
            builder.setSingleChoiceItems(items, -1) { _, which ->
                val choice = items[which]
                productType = items[which]
            }
            builder.setPositiveButton("Submit") { _, _ ->
            }
            dialogOrder = builder.create()
            dialogOrder.show()
        }

        //Attach photo to order
        btn_send_photo.setOnClickListener {
            getPhoto()
        }

        val presenter = OrderPresenter(findViewById(android.R.id.content))
        //create order
        btn_submit.setOnClickListener {
            presenter.submit(
                et_productName.text.toString(),
                et_amount.text.toString().toInt(),
                et_description.text.toString(),
                ParseUser.getCurrentUser(),
                productType,
                photo
            )
        }
    }

    fun getPhoto() {
        val photoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoIntent, 2);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val image = data.data
            photo = MediaStore.Images.Media.getBitmap(contentResolver, image)
        }
    }
}
