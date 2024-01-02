package de.chrisander.wishare.presentation.my_wish

sealed class MyWishViewModelEvent {
    data object NavigateUp: MyWishViewModelEvent()
}