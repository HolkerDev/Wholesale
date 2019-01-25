package com.dev.holker.wholesale.subfragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.UserAdapter
import com.dev.holker.wholesale.model.User
import com.parse.ParseFile
import com.parse.ParseUser
import kotlinx.android.synthetic.main.subfragment_users.*

class UsersSubfragment : Fragment() {

    val mUsers = arrayListOf<User>()
    lateinit var mAdapter: ArrayAdapter<User>
    private val TAG = UsersSubfragment::class.java.name


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = UserAdapter(
            activity!!.applicationContext,
            R.layout.card_user,
            mUsers
        )
        val view = inflater.inflate(R.layout.subfragment_users, container, false)
        return view
    }

    override fun onStart() {
        updateUserList("")
        super.onStart()
    }

    fun updateUserList(filter: String) {
        mUsers.clear()
        val query = ParseUser.getQuery()
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().username)
        query.findInBackground() { objects, e ->
            run {
                if (e == null) {
                    if (objects.size > 0) {
                        for (i: ParseUser in objects) {
                            if (!filter.equals("")) {
                                Log.i("MyLog", "Filter : $filter")
                            } else {
                                val photoUser = i.get("avatar") as ParseFile
                                photoUser.getDataInBackground { data, e ->
                                    if (e != null) {
                                        Log.i("MyLog", e.message.toString())
                                    } else {
                                        mUsers.add(
                                            User(
                                                i.objectId,
                                                i.getString("name"),
                                                BitmapFactory.decodeByteArray(data, 0, data.size),
                                                i.getString("descriprion"),
                                                i.getInt("background")
                                            )
                                        )
                                        lv_users_all.adapter = mAdapter
                                    }

                                }

                            }
                        }

                    } else {
                        Toast.makeText(context, "No users to show", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.i(TAG, "Error with query")
                }
            }
        }
    }

}