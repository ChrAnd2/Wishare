package de.chrisander.wishare.presentation.wishlist_member

import de.chrisander.wishare.domain.model.Wish

sealed class WishlistMemberUiEvent {
    data class OnReserveClicked(val wish: Wish): WishlistMemberUiEvent()
    data class OnBoughtClicked(val wish: Wish): WishlistMemberUiEvent()
    data class OnHandedOverClicked(val wish: Wish): WishlistMemberUiEvent()
    data class OnCancelReservationClicked(val wish: Wish): WishlistMemberUiEvent()
}