package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.presenters.OrderDescriptionPresenter
import kotlinx.android.synthetic.main.activity_order_description.*

class OrderDescription : AppCompatActivity() {

    //TODO:Fix empty image
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_description)

        val presenter = OrderDescriptionPresenter(findViewById(android.R.id.content))

        val bitmap = presenter.downloadPhoto(intent.getStringExtra("id"))
        img_order_descr.setImageBitmap(bitmap)
    }
}
