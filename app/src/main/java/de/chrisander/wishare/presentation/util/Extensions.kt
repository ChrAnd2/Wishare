package de.chrisander.wishare.presentation.util

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray(compression: Int = 100): ByteArray{
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, compression, stream)
    val byteArray = stream.toByteArray()
    recycle()
    return byteArray
}