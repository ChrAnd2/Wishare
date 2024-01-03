package de.chrisander.wishare.presentation.home.settings

sealed class SettingsViewModelEvent {
    data object NavigateToSignIn: SettingsViewModelEvent()
}