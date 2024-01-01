package de.chrisander.wishare.presentation.home.families

import de.chrisander.wishare.domain.model.Family

sealed class FamiliesUiEvent {
    data class OnFamilyClicked(val family: Family): FamiliesUiEvent()
    data class OnEditFamilyClicked(val family: Family): FamiliesUiEvent()
    data object OnCreateFamilyClicked: FamiliesUiEvent()
    data object OnJoinFamilyClicked: FamiliesUiEvent()
}