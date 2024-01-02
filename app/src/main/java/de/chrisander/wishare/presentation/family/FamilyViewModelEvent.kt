package de.chrisander.wishare.presentation.family

sealed class FamilyViewModelEvent {
    data object NavigateUp: FamilyViewModelEvent()
}