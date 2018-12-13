package com.dev.holker.wholesale.presenters.interfaces

import android.graphics.Bitmap

interface IOrderDescriptionPresenter {
    fun downloadPhoto(id: String): Bitmap?
    fun toast(string: String)
}