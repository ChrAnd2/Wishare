package de.chrisander.wishare.presentation.sign_in

import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel: BaseViewModel<SignInState, SignInViewModelEvent, SignInUiEvent>() {
    override val initialScreenState = SignInState()

    override fun onEvent(event: SignInUiEvent) {
        when(event){
            is SignInUiEvent.OnSignInResult -> {
                _state.value = SignInState(
                    isSignInSuccessful = event.result.data != null,
                    signInError = event.result.errorMessage
                )
                viewModelScope.launch {
                    _eventFlow.emit(SignInViewModelEvent.NavigateToHome)
                }
            }
        }
    }
}