package de.chrisander.wishare.presentation.home.settings

import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.data.datasource.IAuthenticationDataSource
import de.chrisander.wishare.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val authenticationDataSource: IAuthenticationDataSource
): BaseViewModel<SettingsScreenState, SettingsViewModelEvent, SettingsUiEvent>() {

    override val initialScreenState = SettingsScreenState(
        userData = authenticationDataSource.getSignedInUser()
    )

    override fun onEvent(event: SettingsUiEvent) {
        when(event){
            SettingsUiEvent.OnSignOutClicked -> viewModelScope.launch {
                authenticationDataSource.signOut()
                _eventFlow.emit(SettingsViewModelEvent.NavigateToSignIn)
            }
        }
    }
}