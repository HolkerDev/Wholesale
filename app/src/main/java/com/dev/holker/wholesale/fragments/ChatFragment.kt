package com.dev.holker.wholesale.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.holker.wholesale.ChatAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.ChatItem
import com.dev.holker.wholesale.model.Functions
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : androidx.fragment.app.Fragment() {

    val mChats = arrayListOf<ChatItem>()
    val objectsIds = HashSet<String>()

    override fun onStart() {
        mChats.clear()

        val queryFirst = ParseQuery<ParseObject>("Chat")
        queryFirst.whereEqualTo("sender", ParseUser.getCurrentUser())
        queryFirst.findInBackground { objects, e ->
            if (e != null) {
                Log.i("ChatFragment", e.message)
            } else {
                if (objects.size < 1) {
                    Functions.toast(context, "No chats!")
                    Log.i("ChatFragment", "First Empty")
                    //start

                    Log.i("ChatFragment", "1")
                    val querySecond = ParseQuery<ParseObject>("Chat")
                    querySecond.whereEqualTo("receiver", ParseUser.getCurrentUser())
                    querySecond.findInBackground { objs, error ->
                        if (error != null) {
                            Log.i("ChatFragment", error.message)
                            Log.i("ChatFragment", "Second Empty")
                        } else {
                            Log.i("ChatFragment", "2")
                            if (objs.size < 1) {
                                Functions.toast(context, "No chats!")

//                                if (mChats.size >= 1) {
//                                    val mAdapter = ChatAdapter(
//                                        activity!!.applicationContext,
//                                        R.layout.item_message,
//                                        mChats
//                                    )
//                                    lv_chat_start.adapter = mAdapter
//                                }


                            } else {
                                Log.i("ChatFragment", "3")
                                for (users in objs) {
                                    val userSecond = users.getParseUser("sender")
                                    if (userSecond != null) {
                                        objectsIds.add(userSecond.objectId)
                                    }
                                }

                                Log.i("ChatFragment", "5")
                                if (objectsIds.size >= 1) {
                                    for (oneUser in objectsIds) {
                                        val queryUser = ParseQuery<ParseUser>("_User")
                                        queryUser.whereEqualTo("objectId", oneUser)
                                        val interlocutor = queryUser.first
                                        mChats.add(
                                            ChatItem(
                                                interlocutor.username,
                                                interlocutor.objectId
                                            )
                                        )
                                        val mAdapter = ChatAdapter(
                                            activity!!.applicationContext,
                                            R.layout.item_message,
                                            mChats
                                        )

                                        lv_chat_start.adapter = mAdapter
                                    }
                                }
                            }
                        }
                    }


                    //end
                } else {
                    Log.i("ChatFragment", "Go here")
                    for (obj in objects) {
                        val user = obj.getParseUser("receiver")
                        if (user != null) {
                            objectsIds.add(user.objectId)
                        }
                    }

                    val querySecond = ParseQuery<ParseObject>("Chat")
                    querySecond.whereEqualTo("receiver", ParseUser.getCurrentUser())
                    querySecond.findInBackground { objs, error ->
                        if (error != null) {
                            Log.i("ChatFragment", error.message)
                            Log.i("ChatFragment", "Third Empty")
                        } else {
                            if (objs.size < 1) {
                                Functions.toast(context, "No chats!")

                                Log.i("ChatFragment", "Go here 2")

                                if (objectsIds.size >= 1) {
                                    for (oneUser in objectsIds) {
                                        val queryUser = ParseQuery<ParseUser>("_User")
                                        queryUser.whereEqualTo("objectId", oneUser)
                                        val interlocutor = queryUser.first
                                        mChats.add(
                                            ChatItem(
                                                interlocutor.username,
                                                interlocutor.objectId
                                            )
                                        )
                                    }
                                }

                                if (mChats.size >= 1) {
                                    val mAdapter = ChatAdapter(
                                        activity!!.applicationContext,
                                        R.layout.item_message,
                                        mChats
                                    )

                                    lv_chat_start.adapter = mAdapter
                                }
                            } else {
                                for (users in objs) {
                                    val userSecond = users.getParseUser("sender")
                                    if (userSecond != null) {
                                        objectsIds.add(userSecond.objectId)
                                    }
                                }

                                if (objectsIds.size >= 1) {
                                    for (oneUser in objectsIds) {
                                        val queryUser = ParseQuery<ParseUser>("_User")
                                        queryUser.whereEqualTo("objectId", oneUser)
                                        val interlocutor = queryUser.first
                                        mChats.add(
                                            ChatItem(
                                                interlocutor.getString("name"),
                                                interlocutor.objectId
                                            )
                                        )
                                        val mAdapter = ChatAdapter(
                                            activity!!.applicationContext,
                                            R.layout.item_message,
                                            mChats
                                        )

                                        lv_chat_start.adapter = mAdapter
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, null)
    }
}