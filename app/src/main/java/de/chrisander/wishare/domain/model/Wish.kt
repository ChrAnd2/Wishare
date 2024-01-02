package de.chrisander.wishare.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.domain.util.WishId
import kotlinx.parcelize.Parcelize
import java.net.URL
import java.util.UUID

@Parcelize
data class Wish(
    val id: WishId = UUID.randomUUID(),
    val name: String,
    val description: String,
    val url: URL?,
    val creationDate: Long,
    val state: WishState,
    val wisherId: FamilyMemberId
): Parcelable
