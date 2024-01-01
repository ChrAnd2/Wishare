package de.chrisander.wishare.presentation.home.families

import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.presentation.home.HomeViewModelEvent

sealed class FamiliesViewModelEvent {
    data object NavigateToCreateFamily: FamiliesViewModelEvent()
    data object NavigateToJoinFamily: FamiliesViewModelEvent()
    data class NavigateToEditFamily(val family: Family): FamiliesViewModelEvent()
}