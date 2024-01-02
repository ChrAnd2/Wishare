package de.chrisander.wishare.presentation.home.my_wishes

import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.util.WishId

sealed class MyWishesViewModelEvent {
    data object NavigateToCreateWish: MyWishesViewModelEvent()
    data class NavigateToEditWish(val wishId: WishId): MyWishesViewModelEvent()
}