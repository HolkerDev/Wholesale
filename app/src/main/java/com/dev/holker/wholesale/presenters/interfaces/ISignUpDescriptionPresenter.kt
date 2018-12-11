package com.dev.holker.wholesale.presenters.interfaces

import android.content.Intent
import android.graphics.Bitmap

interface ISignUpDescriptionPresenter {
    fun signUp(intent: Intent, avatar: Bitmap)
    fun toast(string: String)
    fun goToMain()
    fun getHint(intent: Intent): String
}