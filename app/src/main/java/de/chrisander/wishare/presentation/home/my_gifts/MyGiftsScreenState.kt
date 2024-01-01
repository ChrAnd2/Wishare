package de.chrisander.wishare.presentation.home.my_gifts

import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.util.FamilyMemberId
import java.util.UUID

sealed class MyGiftsScreenState {
    data object Empty : MyGiftsScreenState()
    data class GiftsList(
        val gifts: List<Wish>,
        val ownerId: FamilyMemberId
    ): MyGiftsScreenState()
}
