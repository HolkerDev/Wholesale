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
    lateinit var mAdapter: ChatAdapter

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
                } else {
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
                        } else {
                            if (objs.size < 1) {
                                Functions.toast(context, "No chats!")
                            } else {
                                for (users in objs) {
                                    val userSecond = users.getParseUser("sender")
                                    if (userSecond != null) {
                                        objectsIds.add(userSecond.objectId)
                                    }
                                }

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

                                val mAdapter = ChatAdapter(
                                    activity!!.applicationContext,
                                    R.layout.item_message,
                                    mChats
                                )

                                lv_chat_start.adapter = mAdapter

                                Functions.toast(context, objectsIds.toString())
                            }
                        }
                    }
                }
            }
        }




        super.onStart()
    }


    fun secondSearch() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, null)
    }
}