package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.presenters.OrderPresenter

class Order : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val presenter = OrderPresenter(findViewById(android.R.id.content))
    }
}
