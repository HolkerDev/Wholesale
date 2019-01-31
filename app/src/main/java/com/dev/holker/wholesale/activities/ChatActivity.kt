package com.dev.holker.wholesale.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.MessageItem
import com.dev.holker.wholesale.presenters.ChatPresenter
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    lateinit var sender: ParseUser
    lateinit var receiver: ParseUser
    val mMessages = arrayListOf<MessageItem>()
    lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        presenter = ChatPresenter(findViewById(android.R.id.content))
        presenter.init(intent)

        //find receiver
        val queryReceiver = ParseQuery<ParseUser>("_User")
        queryReceiver.whereEqualTo("objectId", intent.getStringExtra("id"))
        receiver = queryReceiver.first

        //find sender
        sender = ParseUser.getCurrentUser()

        presenter.updateMessagesList(mMessages, lv_messages)

        btn_send.setOnClickListener {
            presenter.sendMessage(et_message, mMessages, lv_messages)
        }
    }


}
