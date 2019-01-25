package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.MessagesAdapter
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.Functions
import com.dev.holker.wholesale.model.MessageItem
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    lateinit var sender: ParseUser
    lateinit var receiver: ParseUser
    val mMessages = arrayListOf<MessageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //TODO:REWRITE HOLE CLASS

        //find receiver
        val queryReceiver = ParseQuery<ParseUser>("_User")
        queryReceiver.whereEqualTo("objectId", intent.getStringExtra("id"))
        receiver = queryReceiver.first

        //find sender
        sender = ParseUser.getCurrentUser()

        updateEverything()

        btn_send.setOnClickListener {
            val message = ParseObject("Chat")
            message.put("message", et_message.text.toString())
            message.put("sender", ParseUser.getCurrentUser())
            message.put("receiver", receiver)
            et_message.setText("")
            message.saveInBackground {
                updateEverything()
            }
        }
    }

    fun updateEverything() {
        mMessages.clear()
        val queryMessages1 = ParseQuery<ParseObject>("Chat")
        queryMessages1.whereEqualTo("sender", sender)
        queryMessages1.whereEqualTo("receiver", receiver)

        //we send to user
        queryMessages1.findInBackground { objects, e ->
            if (e != null) {
                Log.i("ChatActivity", e.message)
            } else {
                if (objects.size < 1) {
                    Toast.makeText(applicationContext, "You have no messages with this user", Toast.LENGTH_LONG).show()
                    //start second search
                    val queryMessagesSecond = ParseQuery<ParseObject>("Chat")
                    queryMessagesSecond.whereEqualTo("sender", receiver)
                    queryMessagesSecond.whereEqualTo("receiver", sender)

                    //user send to us
                    queryMessagesSecond.findInBackground { messasges, error ->
                        if (error != null) {
                            Log.i("ChatActivity", error.message)
                        } else {
                            if (messasges.size < 1) {
                                Toast.makeText(
                                    applicationContext,
                                    "You have no messages with this user",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (mMessages.size >= 1) {
                                    val mAdapter = MessagesAdapter(
                                        applicationContext,
                                        R.layout.item_message,
                                        mMessages
                                    )
                                    lv_messages.adapter = mAdapter
                                }
                            } else {
                                for (message in messasges) {
                                    val date = message.getDate("createdAt")
                                    mMessages.add(
                                        MessageItem(
                                            receiver.objectId,
                                            message.getString("message"),
                                            false,
                                            message.createdAt
                                        )
                                    )
                                }

                                Functions.sortDates(mMessages)

                                val mAdapter = MessagesAdapter(
                                    applicationContext,
                                    R.layout.item_message,
                                    mMessages
                                )
                                lv_messages.adapter = mAdapter
                            }
                        }
                    } //end second search
                } else {
                    Log.i("MyLog", objects.size.toString())
                    for (messageSend in objects) {
                        val date = messageSend.getDate("createdAt")
                        mMessages.add(
                            MessageItem(
                                ParseUser.getCurrentUser().objectId,
                                messageSend.getString("message"),
                                true,
                                messageSend.createdAt
                            )
                        )
                    }

                    val queryMessagesSecond = ParseQuery<ParseObject>("Chat")
                    queryMessagesSecond.whereEqualTo("sender", receiver)
                    queryMessagesSecond.whereEqualTo("receiver", sender)

                    //user send to us
                    queryMessagesSecond.findInBackground { messasges, error ->
                        if (error != null) {
                            Log.i("ChatActivity", error.message)
                        } else {
                            if (messasges.size < 1) {
                                Toast.makeText(
                                    applicationContext,
                                    "You have no messages with this user",
                                    Toast.LENGTH_LONG
                                ).show()

                                if (mMessages.size >= 1) {
                                    val mAdapter = MessagesAdapter(
                                        applicationContext,
                                        R.layout.item_message,
                                        mMessages
                                    )
                                    lv_messages.adapter = mAdapter
                                }
                            } else {
                                for (message in messasges) {
                                    val date = message.getDate("createdAt")
                                    mMessages.add(
                                        MessageItem(
                                            receiver.objectId,
                                            message.getString("message"),
                                            false,
                                            message.createdAt
                                        )
                                    )
                                }

                                Functions.sortDates(mMessages)

                                val mAdapter = MessagesAdapter(
                                    applicationContext,
                                    R.layout.item_message,
                                    mMessages
                                )
                                lv_messages.adapter = mAdapter
                            }
                        }
                    }
                }
            }
        }
    }

}
