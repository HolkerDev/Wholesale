package com.dev.holker.wholesale.presenters.interfaces

import android.widget.ArrayAdapter

interface ISignUpPresenter {
    fun goToNext(name: String, password: String, type: String)
    fun getAdapter(): ArrayAdapter<String>
}