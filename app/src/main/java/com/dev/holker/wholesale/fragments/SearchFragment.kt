package com.dev.holker.wholesale.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.User

class SearchFragment : Fragment() {

    lateinit var mUsers:ArrayList<User>

    fun updateUserList() {
        mUsers.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, null)
    }
}