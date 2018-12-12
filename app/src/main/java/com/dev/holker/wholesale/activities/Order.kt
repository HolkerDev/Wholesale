package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.presenters.OrderPresenter
import kotlinx.android.synthetic.main.activity_order.*

class Order : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val presenter = OrderPresenter(findViewById(android.R.id.content))
        btn_submit.setOnClickListener {
            presenter.submit(et_productName.toString(), et_amount.text.toString().toInt(), et_description.toString())
        }
    }
}
