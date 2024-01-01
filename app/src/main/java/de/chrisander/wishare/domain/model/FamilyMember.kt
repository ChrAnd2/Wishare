package de.chrisander.wishare.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class FamilyMember(
    val id: FamilyMemberId = UUID.randomUUID(),
    val name: String,
    val wishes: List<Wish>
): Parcelable
