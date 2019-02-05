package com.dev.holker.wholesale.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.holker.wholesale.R
import com.dev.holker.wholesale.model.ChatItem
import com.dev.holker.wholesale.presenters.ChatFragmentPresenter
import kotlinx.android.synthetic.main.fragment_chat.*
import android.app.Activity



class ChatFragment : androidx.fragment.app.Fragment() {

    val mChats = arrayListOf<ChatItem>()
    val objectsIds = HashSet<String>()
    lateinit var presenter: ChatFragmentPresenter

    override fun onStart() {

        presenter.updateChatList(mChats, lv_chat_start, objectsIds)

        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, null)

        //init presenter
        presenter = ChatFragmentPresenter((context as Activity).findViewById(android.R.id.content))
        presenter.init()

        return view
    }
}