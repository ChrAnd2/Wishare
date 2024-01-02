package de.chrisander.wishare.presentation.home.my_wishes

import de.chrisander.wishare.domain.model.Wish

sealed class MyWishesUiEvent {
    data object OnCreateWishClicked: MyWishesUiEvent()
    data class OnEditWishClicked(val wish: Wish): MyWishesUiEvent()
}