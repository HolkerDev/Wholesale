package com.dev.holker.wholesale.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.SelectionsPagerAdapter
import com.dev.holker.wholesale.UserAdapter
import com.dev.holker.wholesale.model.User
import com.google.android.material.tabs.TabLayout
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : androidx.fragment.app.Fragment() {

    val mUsers = arrayListOf<User>()
    lateinit var mAdapter: ArrayAdapter<User>
    private lateinit var mSelectionsPagerAdapter: SelectionsPagerAdapter
    private lateinit var mViewPager: ViewPager

    fun toast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    //fill listview
    //TODO:Add avatar to user
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
                                mUsers.add(
                                    User(
                                        i.objectId,
                                        i.username,
                                        null,
                                        i.getString("descriprion"),
                                        i.getInt("background")
                                    )
                                )
                            }
                        }
                        lv_users.adapter = mAdapter
                    } else {
                        toast("No objects!")
                    }
                } else {
                    toast(e.message.toString())
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()

        mSelectionsPagerAdapter = SelectionsPagerAdapter(fragmentManager)

        setupWithPager()


        tabs.setupWithViewPager(container)


        updateUserList("")
    }

    private fun setupWithPager() {
        mSelectionsPagerAdapter = SelectionsPagerAdapter(fragmentManager)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mAdapter = UserAdapter(
            activity!!.applicationContext,
            R.layout.card_user,
            mUsers
        )
        return inflater.inflate(R.layout.fragment_search, null)
    }
}
