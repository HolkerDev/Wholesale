package com.dev.holker.wholesale.presenters

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import com.dev.holker.wholesale.activities.MainActivity
import com.dev.holker.wholesale.model.Location
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseUser
import java.io.ByteArrayOutputStream

class SignUpDescriptionPresenter(val view: View) {
    var location: Location = Location("Poland", "Rzeszow", "Sucharskiego")

    //Show AlertDialog with multichoice items
    fun addInterests() {
        val dialog: AlertDialog
        val checkedItems = booleanArrayOf(false, false, false, false)
        val items = arrayOf("Architecture", "Carpets", "Sofas", "Chairs")

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Select interests")
        builder.setMultiChoiceItems(items, checkedItems) { dialog, which, isChecked ->
            checkedItems[which] = isChecked
            // Get the clicked item
            val choice = items[which]

            // Display the clicked item text
            toast("$choice clicked.")
        }

        builder.setPositiveButton("OK") { _, _ ->
            //TODO: Return answer
        }
        dialog = builder.create()
        dialog.show()
    }


    fun selectBackground() {
        val dialog: AlertDialog
        val items = arrayOf("Chinese house", "Garden", "Office", "Rand")
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Select background")
        builder.setSingleChoiceItems(items, -1) { _, which ->
            val choice = items[which]
            toast("$choice clicked!")
        }
        builder.setPositiveButton("Yeah") { _, _ ->
            //TODO: Return answer
        }
        dialog = builder.create()
        dialog.show()
    }

    fun goToMain() {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        view.context.startActivity(intent)
    }

    fun getHint(intent: Intent): String {
        return intent.getStringExtra("role")
    }


    fun signUp(intent: Intent, avatar: Bitmap) {
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


    fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }
}