package com.dev.holker.wholesale.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dev.holker.wholesale.R
import com.parse.ParseUser
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {

    lateinit var mUsers: ArrayList<String>
    lateinit var mAdapter: ArrayAdapter<String>


    fun toast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun updateUserList() {
        mUsers.clear()
        val query = ParseUser.getQuery()
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().username)
        query.findInBackground() { objects, e ->
            run {
                if (e == null) {
                    if (objects.size > 0) {
                        for (i: ParseUser in objects) {
                            mUsers.add(i.username)
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
        mUsers = arrayListOf<String>()

        mAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, mUsers);
        updateUserList()

        io.reactivex.Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
            .map { text -> text.toLowerCase().trim() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .subscribe { text ->
                Log.d("MyLog", "subscriber: $text")
            }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_search, null)
    }
}