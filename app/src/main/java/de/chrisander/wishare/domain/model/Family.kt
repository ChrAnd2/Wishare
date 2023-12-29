package de.chrisander.wishare.domain.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

data class Family(
    val name: String,
    val image: Bitmap,
    val members: List<FamilyMember>
)
