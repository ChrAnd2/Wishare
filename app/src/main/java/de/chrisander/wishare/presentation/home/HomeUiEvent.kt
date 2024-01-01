package de.chrisander.wishare.presentation.home

import de.chrisander.wishare.presentation.home.components.BottomMenuContent

sealed class HomeUiEvent {
    data class OnBottomMenuItemClicked(val bottomMenuContent: BottomMenuContent): HomeUiEvent()
}