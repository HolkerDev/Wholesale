package com.dev.holker.wholesale

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.model.InfoDialog
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        edit_info.setOnClickListener {
            val dialog = InfoDialog()
            val fm = supportFragmentManager
            dialog.show(fm, "Information")
        }

        edit_go_back.setOnClickListener {
            finish()
        }
    }
}
