package com.dev.holker.wholesale

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dev.holker.wholesale.activities.ChatActivity
import com.dev.holker.wholesale.model.ChatItem
import kotlinx.android.synthetic.main.item_chat.view.*
import java.util.*

class ChatAdapter(
    private val mContext: Context,
    private val mResource: Int,
    private val mObjects: ArrayList<ChatItem>
) : ArrayAdapter<ChatItem>(mContext, mResource, mObjects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.item_chat, null)

        val chat = mObjects[position]

        view.chat_go_to_chat.setOnClickListener {
            val intent = Intent(mContext, ChatActivity::class.java)
            intent.putExtra("id", chat.id)
            mContext.startActivity(intent)
        }

        view.chat_username.setText(chat.name)

        return view

    }
}