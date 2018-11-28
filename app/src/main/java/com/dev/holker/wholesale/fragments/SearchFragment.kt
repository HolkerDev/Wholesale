package com.dev.holker.wholesale.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.User
import com.dev.holker.wholesale.UserAdapter
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    lateinit var mUsers: ArrayList<User>
    lateinit var mAdapter:UserAdapter

    fun updateUserList() {
        mUsers.clear()
        val query = ParseQuery<ParseObject>("User")
        query.whereNotEqualTo("name", ParseUser.getCurrentUser().username)
        query.findInBackground() { objects, e ->
            run {
                if (objects.size > 0 && e == null) {
                    for (i: ParseObject in objects) {
                        mUsers.add(User(i.getString("name")!!, i.getParseFile("avatar")!!, i.getString("description")!!))
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lv_users.adapter
        updateUserList()
        //TODO: append adapter

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, null)
    }
}