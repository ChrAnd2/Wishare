package de.chrisander.wishare.presentation.home.my_gifts

import de.chrisander.wishare.domain.model.Wish

sealed class MyGiftsUiEvent {
    data class OnReserveClicked(val wish: Wish): MyGiftsUiEvent()
    data class OnBoughtClicked(val wish: Wish): MyGiftsUiEvent()
    data class OnCancelReservationClicked(val wish: Wish): MyGiftsUiEvent()
}