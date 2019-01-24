package com.dev.holker.wholesale.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.MessagesAdapter
import com.dev.holker.wholesale.R
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

        //find receiver
        val queryReceiver = ParseQuery<ParseUser>("_User")
        queryReceiver.whereEqualTo("objectId", intent.getStringExtra("id"))
        receiver = queryReceiver.first

        //find sender
        sender = ParseUser.getCurrentUser()

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
                } else {
                    for (message in objects) {
                        val date = message.getDate("createdAt")
                        mMessages.add(
                            MessageItem(
                                ParseUser.getCurrentUser().objectId,
                                message.getString("message"),
                                true,
                                date
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
                            } else {
                                for (message in messasges) {
                                    val date = message.getDate("createdAt")
                                    mMessages.add(
                                        MessageItem(
                                            receiver.objectId,
                                            message.getString("message"),
                                            false,
                                            date
                                        )
                                    )
                                }

                                //TODO:Add sorting by dates


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
