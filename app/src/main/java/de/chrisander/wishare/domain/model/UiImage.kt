package de.chrisander.wishare.domain.model

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import de.chrisander.wishare.presentation.util.toByteArray
import kotlinx.parcelize.Parcelize

sealed class UiImage: Parcelable {

    abstract fun getBitmap(context: Context): Bitmap
    abstract override fun equals(other: Any?): Boolean

    @Parcelize
    data class Dynamic(
        val imageBytes: ByteArray
    ): UiImage(){

        constructor(bitmap: Bitmap): this(bitmap.toByteArray())
        override fun getBitmap(context: Context): Bitmap {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        override fun equals(other: Any?): Boolean {
            if(this === other) return true
            if(other == null) return false
            if(other !is Dynamic) return false
            return imageBytes.contentEquals(other.imageBytes)
        }

        override fun hashCode(): Int {
            return imageBytes.contentHashCode()
        }
    }

    @Parcelize
    data class Static(
        @DrawableRes
        val imageRes: Int
    ): UiImage(){
        override fun getBitmap(context: Context): Bitmap {
            return ContextCompat.getDrawable(context, imageRes)?.toBitmap()
                ?: throw NotFoundException("Image with id $imageRes not found")
        }

        override fun equals(other: Any?): Boolean {
            if(this === other) return true
            if(other == null) return false
            if(other !is Static) return false
            return imageRes == other.imageRes
        }

        override fun hashCode(): Int {
            return imageRes
        }
    }

    fun areContentsTheSame(other: UiImage): Boolean {
        if(other::class.java != this::class.java)
            return false

        return when(this){
            is Dynamic -> this.imageBytes.contentEquals((other as Dynamic).imageBytes)
            is Static -> this.imageRes == (other as? Static)?.imageRes
        }
    }
}