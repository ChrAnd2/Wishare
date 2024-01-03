package de.chrisander.wishare.presentation.sign_in

sealed class SignInViewModelEvent {
    data object NavigateToHome: SignInViewModelEvent()
}