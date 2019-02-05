package com.dev.holker.wholesale.presenters

import android.util.Log
import android.view.View
import android.widget.ListView
import com.dev.holker.wholesale.ChatAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.ChatItem
import com.dev.holker.wholesale.model.Wholesale
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class ChatFragmentPresenter(val view: View) {

    lateinit var currentUser: ParseUser

    fun init() {
        currentUser = ParseUser.getCurrentUser()
    }

    fun updateChatList(mChats: ArrayList<ChatItem>, listView: ListView, objectsIds: HashSet<String>) {
        mChats.clear()
        firstQuery(mChats, objectsIds, listView)
    }

    //search for dialogues where current user is sender
    fun firstQuery(mChats: ArrayList<ChatItem>, objectsIds: HashSet<String>, listView: ListView) {
        val queryFirst = ParseQuery<ParseObject>("Chat")
        queryFirst.whereEqualTo("sender", ParseUser.getCurrentUser())
        queryFirst.findInBackground { objects, e ->
            if (e != null) {
                Log.i("ChatFragment", e.message)
            } else {
                // if first query is empty
                if (objects.size < 1) {
                    Log.i("ChatFragment", "First chat query is empty")

                    secondQuery(mChats, objectsIds, listView)
                    //if query is not empty
                } else {

                    Log.i("ChatFragment", "First chat query isn't empty")

                    for (obj in objects) {

                        //add users id to HashSet
                        val user = obj.getParseUser("receiver")
                        if (user != null) {
                            objectsIds.add(user.objectId)
                        }
                    }

                    secondQuery(mChats, objectsIds, listView)
                }
            }
        }
    }

    //search for dialogues where current user is receiver
    fun secondQuery(mChats: ArrayList<ChatItem>, objectsIds: HashSet<String>, listView: ListView) {
        val querySecond = ParseQuery<ParseObject>("Chat")
        querySecond.whereEqualTo("receiver", currentUser)

        querySecond.findInBackground { objects, error ->
            if (error != null) {

                Log.i("ChatFragment", error.message)
                Log.i("ChatFragment", "Second query is empty")

            } else {
                //if query is empty
                if (objects.size < 1) {

                    Wholesale.toast(view.context, "No chats!")

                    Log.i("ChatFragment", "Go here 2")

                    //if query is empty, but we have results from first query
                    if (objectsIds.size >= 1) {

                        for (userId in objectsIds) {
                            addChats(userId, mChats)
                        }
                    }

                    //if we have chats, then we show it
                    if (mChats.size >= 1) {
                        attachAdapter(listView, mChats)
                    }

                    //if query is not empty
                } else {

                    for (users in objects) {

                        val userSecond = users.getParseUser("sender")
                        //add unique user id to HashSet
                        if (userSecond != null) {
                            objectsIds.add(userSecond.objectId)
                        }

                    }

                    // after adding new users adding chats with them
                    if (objectsIds.size >= 1) {

                        for (userId in objectsIds) {
                            addChats(userId, mChats)
                        }

                        attachAdapter(listView, mChats)
                    }
                }
            }
        }
    }

    private fun addChats(userId: String, mChats: ArrayList<ChatItem>) {

        val queryUser = ParseQuery<ParseUser>("_User")
        queryUser.whereEqualTo("objectId", userId)
        val interlocutor = queryUser.first

        mChats.add(
            ChatItem(
                interlocutor.getString("name"),
                interlocutor.objectId
            )
        )
    }

    private fun attachAdapter(listView: ListView, mChats: ArrayList<ChatItem>) {
        val mAdapter = ChatAdapter(
            view.context,
            R.layout.item_message,
            mChats
        )

        listView.adapter = mAdapter
    }

}