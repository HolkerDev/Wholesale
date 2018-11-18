package com.dev.holker.wholesale.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dev.holker.wholesale.R
import kotlinx.android.synthetic.main.activity_order_description.*

class OrderDescription : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_description)

        val intent = intent
        tv_test.text = intent.getStringExtra("order")
    }
}
