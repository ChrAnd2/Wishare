package de.chrisander.wishare.presentation.home.my_wishes

import de.chrisander.wishare.presentation.base.BaseViewModel

class MyWishesViewModel(): BaseViewModel<MyWishesScreenState, MyWishesViewModelEvent, MyWishesUiEvent>() {

    override val initialScreenState = MyWishesScreenState.Empty

    override fun onEvent(event: MyWishesUiEvent) {
        TODO("Not yet implemented")
    }
}