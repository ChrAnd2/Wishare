package de.chrisander.wishare.presentation.wishlist_members

import de.chrisander.wishare.domain.util.FamilyMemberId

sealed class WishlistMembersViewModelEvent {
    data class NavigateToMemberWishlist(val memberId: FamilyMemberId): WishlistMembersViewModelEvent()
}