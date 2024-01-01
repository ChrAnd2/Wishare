package de.chrisander.wishare.domain.model

import android.graphics.Bitmap
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Family(
    val name: String,
    val image: Bitmap,
    val memberIds: List<FamilyMemberId>
): Parcelable
