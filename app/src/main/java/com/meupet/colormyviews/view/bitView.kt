package com.meupet.colormyviews.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.ImageView

public class bitView {
    fun getBitMapFromView(view:ImageView ):Bitmap?{
    val bitmap =
        Bitmap.createBitmap(view.width,view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}