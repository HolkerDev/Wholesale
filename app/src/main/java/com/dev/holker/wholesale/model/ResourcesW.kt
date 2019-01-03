package com.dev.holker.wholesale.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dev.holker.wholesale.R
//TODO:Fix 'First frame was drawed before optimized, so skip!'

class ResourcesW {
    companion object {
        fun getBackground(number: Int, context: Context): Bitmap {
            when (number) {
                0 -> {
                    return BitmapFactory.decodeResource(context.resources, R.drawable.b_architecture_1)
                }
                1 -> {
                    return BitmapFactory.decodeResource(context.resources, R.drawable.b_garden)
                }
                2 -> {
                    return BitmapFactory.decodeResource(context.resources, R.drawable.b_office)
                }
                else -> {
                    return BitmapFactory.decodeResource(context.resources, R.drawable.b_architecture_1)
                }
            }
        }
    }
}