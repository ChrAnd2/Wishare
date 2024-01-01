package de.chrisander.wishare.presentation.home.families

import de.chrisander.wishare.domain.model.Family

sealed class FamiliesScreenState {
    data object Empty : FamiliesScreenState()
    data class FamiliesList(val families: List<Family>) : FamiliesScreenState()
}