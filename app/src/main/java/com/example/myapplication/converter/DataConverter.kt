package com.example.myapplication.converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.ByteArrayOutputStream

object DataConverter {
    fun getbytes(bitmap: Bitmap):ByteArray {
        val byte = ByteArrayOutputStream()
       bitmap.compress(Bitmap.CompressFormat.PNG,0,byte)
        return byte.toByteArray()
    }
    fun getImage(imageView: ByteArray):Bitmap{
        return  BitmapFactory.decodeByteArray(imageView, 0,imageView.size)
    }
}