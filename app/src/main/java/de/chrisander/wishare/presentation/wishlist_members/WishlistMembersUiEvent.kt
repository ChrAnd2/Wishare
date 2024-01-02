package de.chrisander.wishare.presentation.wishlist_members

import de.chrisander.wishare.domain.model.FamilyMember

sealed class WishlistMembersUiEvent {
    data class OnMemberClicked(val member: FamilyMember): WishlistMembersUiEvent()
}