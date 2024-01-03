package de.chrisander.wishare.presentation.sign_in

sealed class SignInUiEvent {
    data class OnSignInResult(val result: SignInResult): SignInUiEvent()
}