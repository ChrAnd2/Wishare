package de.chrisander.wishare.domain.model

import android.os.Parcelable
import de.chrisander.wishare.domain.util.FamilyId
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Family(
    val id: FamilyId = UUID.randomUUID(),
    val name: String,
    val image: UiImage,
    val memberIds: List<FamilyMemberId>
): Parcelable
