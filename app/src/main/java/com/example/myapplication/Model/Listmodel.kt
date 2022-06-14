package com.example.myapplication.Model

import android.graphics.Bitmap
import java.sql.Blob
import kotlin.jvm.internal.Ref

data class Listmodel (
    var productID: Int? = null,
    var productName: String? = null,
    var productPrice: String? = null,
    var productImage: Bitmap? = null
)