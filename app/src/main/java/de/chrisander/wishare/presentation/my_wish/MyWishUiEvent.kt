package de.chrisander.wishare.presentation.my_wish

sealed class MyWishUiEvent {
    data class OnWishTitleChanged(val newTitle: String): MyWishUiEvent()
    data class OnWishDescriptionChanged(val newDescription: String): MyWishUiEvent()
    data class OnWishUrlChanged(val newUrl: String): MyWishUiEvent()
    data object OnWishRemoveClicked: MyWishUiEvent()
}