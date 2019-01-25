package com.dev.holker.wholesale.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.activities.Login
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseRole
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : androidx.fragment.app.Fragment() {


    override fun onStart() {
        super.onStart()

        val currentUser = ParseUser.getCurrentUser()

        profile_descr.setText(currentUser.getString("description"))


        val queryRole = ParseQuery<ParseRole>("_Role")
        queryRole.whereEqualTo("users", ParseUser.getCurrentUser())
        val role = queryRole.first

        if (role.getNumber("roleId") == 2) {
            Log.i("MyLog", "it's a client")
            profile_type.setText("Client")
        } else if (role.getNumber("roleId") == 3) {
            Log.i("MyLog", "it's a supplier")
            profile_type.setText("Supplier")
        }

        profile_name.setText(currentUser.getString("name"))


        val locationObj = currentUser.getParseObject("location")

        var location = locationObj!!.fetchIfNeeded<ParseObject>().getString("street")

        val cityObj = locationObj.getParseObject("city")

        location += ",\n" + cityObj!!.fetchIfNeeded<ParseObject>().getString("name")

        val countryObj = locationObj.getParseObject("country")

        location += ", " + countryObj!!.fetchIfNeeded<ParseObject>().getString("name")

        profile_location.setText(location)

        val queryRating = ParseQuery<ParseObject>("Rating")
        queryRating.whereEqualTo("user", currentUser)
        val ratingObj = queryRating.first

        val rating = ratingObj.getNumber("rate")!!.toInt()

        profile_rating.setText(rating.toString())
        if (rating == 0) {
            profile_rating.background =
                ColorDrawable(ContextCompat.getColor(activity!!.applicationContext, R.color.colorBlue))
        } else if (rating > 0) {
            profile_rating.background =
                ColorDrawable(ContextCompat.getColor(activity!!.applicationContext, R.color.colorGreen))
        } else {
            profile_rating.background =
                ColorDrawable(ContextCompat.getColor(activity!!.applicationContext, R.color.colorRed))
        }



        btn_profile_logout.setOnClickListener {
            ParseUser.logOut()
            val i = Intent(activity!!.applicationContext, Login::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        val photoUser = currentUser.getParseFile("avatar")

        photoUser!!.getDataInBackground { data, e ->
            if (e != null) {
                Log.i("ProfileFragment", e.message)
            } else {
                if (data != null) {
                    profile_profile_image.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.size))
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, null)
    }
}
