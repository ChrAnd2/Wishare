package de.chrisander.wishare.presentation.home.settings

sealed class SettingsUiEvent {
    data object OnSignOutClicked: SettingsUiEvent()
}