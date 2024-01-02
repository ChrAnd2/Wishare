package de.chrisander.wishare.presentation.family

import de.chrisander.wishare.domain.model.UiImage

sealed class FamilyUiEvent {
    data class OnFamilyImageChanged(val newImage: UiImage): FamilyUiEvent()
    data class OnFamilyNameChanged(val newName: String): FamilyUiEvent()
    data object OnRemoveClicked: FamilyUiEvent()
}