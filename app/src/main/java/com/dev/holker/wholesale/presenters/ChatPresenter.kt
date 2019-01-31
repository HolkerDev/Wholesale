package com.dev.holker.wholesale.presenters

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ListView
import com.dev.holker.wholesale.MessagesAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.Functions
import com.dev.holker.wholesale.model.MessageItem
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class ChatPresenter(val view: View) {

    lateinit var sender: ParseUser
    lateinit var receiver: ParseUser

    fun init(intent: Intent) {
        val queryReceiver = ParseQuery<ParseUser>("_User")
        queryReceiver.whereEqualTo("objectId", intent.getStringExtra("id"))
        receiver = queryReceiver.first

        //find sender
        sender = ParseUser.getCurrentUser()
    }


    fun secondQueryMessages(mMessages: ArrayList<MessageItem>, listView: ListView) {
        val queryMessagesSecond = ParseQuery<ParseObject>("Chat")
        queryMessagesSecond.whereEqualTo("sender", receiver)
        queryMessagesSecond.whereEqualTo("receiver", sender)

        //user send to us
        queryMessagesSecond.findInBackground { messages, error ->
            if (error != null) {
                Log.i("ChatActivity", error.message)
            } else {
                //if this query has no results
                if (messages.size < 1) {

                    Log.i("ChatActivity", "QueryMessagesSecond messages list is empty")

                    //if first query had any results, then we attach it to listview
                    if (mMessages.size >= 1) {
                        //sort messages by date
                        Functions.sortDates(mMessages)

                        //create adapter for listview
                        val mAdapter = MessagesAdapter(
                            view.context,
                            R.layout.item_message,
                            mMessages
                        )

                        //attach adapter to listview
                        listView.adapter = mAdapter

                        //scroll down listview
                        listView.setSelection(mAdapter.count - 1)

                    }
                } else {
                    //if second query has results
                    Log.i("ChatActivity", "QueryMessagesSecond has results")

                    //loop through the results of query
                    for (message in messages) {

                        //get date of this message from Parse
                        val date = message.getDate("createdAt")

                        //add message to list of messages
                        mMessages.add(
                            MessageItem(
                                receiver.objectId,
                                message.getString("message"),
                                false,
                                message.createdAt
                            )
                        )
                    }

                    //sort messages by date
                    Functions.sortDates(mMessages)

                    //create adapter for listview
                    val mAdapter = MessagesAdapter(
                        view.context,
                        R.layout.item_message,
                        mMessages
                    )

                    //attach adapter to listview
                    listView.adapter = mAdapter
                    //scroll down listview
                    listView.setSelection(mAdapter.count - 1)
                }
            }
        }
    }

}