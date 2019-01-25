package com.dev.holker.wholesale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.dev.holker.wholesale.model.MessageItem
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.item_message.view.*
import java.util.*

class MessagesAdapter(
    private val mContext: Context,
    private val mResource: Int,
    private val mObjects: ArrayList<MessageItem>
) : ArrayAdapter<MessageItem>(mContext, mResource, mObjects) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(mContext)

        val view = inflater.inflate(R.layout.item_message, null)


        val messageItem = mObjects[position]

        view.tv_message_text.text = "  " + messageItem.text

        if (messageItem.owner) {
            view.tv_message_text.background =
                ContextCompat.getDrawable(mContext, R.drawable.gap_message_blue)
            view.tv_message_text.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite))
            view.tv_message_user.setText("You")
        } else {
            val query = ParseQuery<ParseUser>("_User")
            query.whereEqualTo("objectId", messageItem.userId)
            val userIn = query.first
            view.tv_message_user.setText(userIn.getString("name"))
        }

        return view

    }
}