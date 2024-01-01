package de.chrisander.wishare.presentation.home.my_wishes

import de.chrisander.wishare.domain.model.Wish

sealed class MyWishesScreenState {
    data object Empty: MyWishesScreenState()
    data class MyWishesList(
        val wishes: List<Wish>
    ): MyWishesScreenState()
}
