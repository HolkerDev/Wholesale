package com.dev.holker.wholesale.presenters

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import com.dev.holker.wholesale.activities.MainActivity
import com.dev.holker.wholesale.presenters.interfaces.ISignUpDescriptionPresenter
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseUser
import java.io.ByteArrayOutputStream

class SignUpDescriptionPresenter(val view: View) : ISignUpDescriptionPresenter {

    override fun goToMain() {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        view.context.startActivity(intent)
    }

    override fun getHint(intent: Intent): String {
        return intent.getStringExtra("role")
    }


    override fun signUp(intent: Intent, avatar: Bitmap) {
        val user = ParseUser()
        user.username = intent.getStringExtra("username")
        user.setPassword(intent.getStringExtra("password"))

        user.signUpInBackground {
            if (it == null) {

                //save a role
                val query = ParseQuery<ParseRole>("_Role")
                query.whereEqualTo("name", intent.getStringExtra("role"))
                val role = query.first
                role.users.add(user)
                role.save()
                user.put("role", role)

                val stream = ByteArrayOutputStream()
                avatar.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val bytes = stream.toByteArray()

                val file = ParseFile("avatar.png", bytes)
                user.put("avatar", file)

                user.saveInBackground() {
                    if (it != null) {
                        toast("Error while registration process")
                    } else {
                        toast("Hallelujah")
                        goToMain()
                    }
                }
            } else {
                toast(it.message.toString())
            }
        }
    }

    override fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }

}