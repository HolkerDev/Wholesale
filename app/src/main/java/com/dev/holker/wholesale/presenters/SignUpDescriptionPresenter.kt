package com.dev.holker.wholesale.presenters

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dev.holker.wholesale.activities.MainActivity
import com.dev.holker.wholesale.model.Location
import com.parse.*
import java.io.ByteArrayOutputStream

class SignUpDescriptionPresenter(val view: View) {
    private var background: Int = 4
    private lateinit var interest: ArrayList<String>

    //Show AlertDialog with multichoice items
    fun addInterests(): ArrayList<String> {
        val dialog: AlertDialog
        val interests = ArrayList<String>()
        val checkedItems = booleanArrayOf(false, false, false, false, false, false)
        val items = arrayOf(
            "Dining Sets", "Outdoor Lounge Chairs", "Banquet Chairs", "Tables",
            "High chairs", "Bar Stools"
        )

        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Select interests")
        builder.setMultiChoiceItems(items, checkedItems) { _, which, isChecked ->
            checkedItems[which] = isChecked
            // Get the clicked item
            val choice = items[which]
            if (checkedItems[which] == true) {
                interests.add(choice)
            } else {
                interests.remove(items[which])
            }
            // Display the clicked item text
            toast("$choice clicked.")
        }

        builder.setPositiveButton("OK") { _, _ ->
            this.interest = interests
        }
        dialog = builder.create()
        dialog.show()
        return interests
    }

    //Creates dialog for choosing background
    fun selectBackground() {
        val dialog: AlertDialog
        val items = arrayOf("Chinese house", "Garden", "Office", "Rand")
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Select background")
        builder.setSingleChoiceItems(items, -1) { _, which ->
            val choice = items[which]
            toast("$choice clicked!")
            background = which
        }
        builder.setPositiveButton("Submit") { _, _ ->
        }
        dialog = builder.create()
        dialog.show()
    }

    fun goToMain() {
        val intent = Intent(view.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        view.context.startActivity(intent)
    }

    //Function return hint text for Name gap
    fun getHint(intent: Intent): String {

        if (intent.getStringExtra("role").equals("Client")) {
            return "Your name"
        } else {
            return "Company name"
        }
    }


    fun signUp(intent: Intent, avatar: Bitmap, location: Location, name: String, description: String) {
        val user = ParseUser()
        user.username = intent.getStringExtra("username")
        user.setPassword(intent.getStringExtra("password"))

        user.signUpInBackground {
            if (it == null) {


                //Attach and save a role for user
                val query = ParseQuery<ParseRole>("_Role")
                query.whereEqualTo("name", intent.getStringExtra("role"))
                val role = query.first
                role.users.add(user)
                role.save()
                user.put("role", role)
                user.put("name", name)
                user.put("description", description)
                user.put("background", background)

                //Compressing image to bite array
                val stream = ByteArrayOutputStream()
                avatar.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val bytes = stream.toByteArray()

                //Create new object in table Location
                val obj = ParseObject("Location")
                //Put street to this table
                obj.put("street", location.street)

                //Query for finding a city
                val queryCity = ParseQuery<ParseObject>("City")
                queryCity.whereEqualTo("objectId", getCityId(location.city))
                val objCity = queryCity.first

                //Then put it in Location table like a pointer
                obj.put("city", objCity)

                //Query for finding an country.
                val queryCountry = ParseQuery<ParseObject>("Country")
                queryCountry.whereEqualTo("objectId", getCountryId(location.country))
                val objCountry = queryCountry.first
                //Then put it in Location table like a pointer
                obj.put("country", objCountry)

                //Save location obj
                obj.saveInBackground() {
                    if (it != null) {
                        Log.i("MyLog", it.message.toString())
                    } else {
                        //Get this object again
                        val locationObj = obj
                        //put location to user
                        user.put("location", locationObj)
                        //LOGS
                        Log.i("MyLog", obj.objectId.toString())
                        Log.i("MyLog", "Done with location")
                        //Convert image byte array to ParseFile
                        val file = ParseFile("avatar.png", bytes)
                        //Attach this file(image) to user
                        user.put("avatar", file)

                        //Save user
                        user.saveInBackground() {
                            if (it != null) {
                                toast("Error while registration process")
                            } else {
                                //loop threw interests
                                for (string: String in interest) {
                                    val preference = ParseObject("Preference")
                                    //put pointer of user to each preference object
                                    preference.put("user", user)
                                    //query productType object
                                    val queryType = ParseQuery<ParseObject>("ProductType")
                                    queryType.whereEqualTo("name", string)
                                    val type = queryType.first
                                    //put this object to preference
                                    preference.put("productType", type)
                                    //save preference
                                    preference.saveInBackground {
                                        val ratingObj = ParseObject("Rating")
                                        ratingObj.put("user", user)
                                        ratingObj.put("rate", 0)
                                        ratingObj.saveInBackground()
                                    }
                                }

                                toast("Hallelujah")
                                //Go to main activity
                                goToMain()
                            }
                        }
                    }
                }
            } else {
                toast(it.message.toString())
            }
        }
    }

    companion object {

        //Function gets name of country and returns objectId from server
        fun getCountryId(country: String?): String {
            when (country) {
                "Ukraine" -> {
                    return "tPwElQRFQg"
                }
                "Russia" -> {
                    return "l3l9QpsnLo"
                }
                "Poland" -> {
                    return "PCH76fXtrn"
                }
                "UK" -> {
                    return "44sDh3COMk"
                }
                "USA" -> {
                    return "Xn5biIk2ea"
                }
                else -> {
                    return "PCH76fXtrn"
                }
            }
        }

        //looks terrible, but I can't make it easier
        //Function gets name of city and returns objectId from server
        fun getCityId(city: String?): String {
            when (city) {
                "London" -> return "wlnKfUpFwK"
                "Cracow" -> return "LdGK8NgugX"
                "Rzeszow" -> return "dQAVroflnm"
                "Warsaw" -> return "n7OBJhPnjY"
                "Poznan" -> return "FkGEmnQFsW"
                "Wroclaw" -> return "E50DqDKfXM"
                "Leeds" -> return "BWRUnBunvn"
                "Liverpool" -> return "V9dbI3N7LG"
                "Edinburgh" -> return "TJdhPxr4Bj"
                "Belfast" -> return "X38zcx7NJZ"
                "Dnipro" -> return "r94iHP9BK4"
                "Kiev" -> return "py69HQHy6c"
                "Ternopil" -> return "5gZUYFkMmd"
                "Lviv" -> return "DLWLRqF9vk"
                "Odessa" -> return "RoT3o3bQxc"
                "Saint Petersburg" -> return "HtNn3qX6iJ"
                "Moscow" -> return "K3HAaAK6AK"
                "Novosibirsk" -> return "EfVc36uER3"
                "Yekaterinburg" -> return "tV8AhLbdeD"
                "Rostov-on-Don" -> return "a5qDwHEZh7"
                "New York" -> return "K9oCQgg4Zd"
                "San Francisco" -> return "UXNkGY4tNv"
                "Washington, D.C." -> return "N04iZLvGNR"
                "Boston" -> return "FkUL5HFwW1"
                "Chicago" -> return "mj9NcjVdZx"
                else -> return "dQAVroflnm"
            }
        }
    }

    fun toast(string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }
}