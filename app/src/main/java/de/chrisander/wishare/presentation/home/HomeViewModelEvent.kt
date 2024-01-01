package de.chrisander.wishare.presentation.home

import de.chrisander.wishare.domain.model.Family

sealed class HomeViewModelEvent {
    data object NavigateToCreateFamily: HomeViewModelEvent()
    data object NavigateToJoinFamily: HomeViewModelEvent()
    data class NavigateToEditFamily(val family: Family): HomeViewModelEvent()
}