package com.dev.holker.wholesale.presenters

import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import com.dev.holker.wholesale.activities.SignupDescription
import com.dev.holker.wholesale.presenters.interfaces.ISignUpPresenter

class SignUpPresenter(val view: View) : ISignUpPresenter {

    override fun goToNext(name: String, password: String, type: String) {
        val intent = Intent(view.context, SignupDescription::class.java)
        intent.putExtra("username", name)
        intent.putExtra("password", password)
        intent.putExtra("role", type)
        view.context.startActivity(intent)
    }

    override fun getAdapter(): ArrayAdapter<String> {
        val listOfRoles = listOf<String>("Client", "Supplier")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(view.context, android.R.layout.simple_spinner_item, listOfRoles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

}