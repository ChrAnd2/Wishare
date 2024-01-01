package de.chrisander.wishare.presentation.home.families

import de.chrisander.wishare.presentation.base.BaseViewModel
import org.koin.core.component.KoinComponent

class FamiliesViewModel(): BaseViewModel<FamiliesScreenState, FamiliesViewModelEvent, FamiliesUiEvent>(), KoinComponent {

    override val initialScreenState = FamiliesScreenState.Empty

    override fun onEvent(event: FamiliesUiEvent) {
        when(event){
            is FamiliesUiEvent.OnFamilyClicked -> {

            }
            is FamiliesUiEvent.OnEditFamilyClicked -> {

            }
            FamiliesUiEvent.OnCreateFamilyClicked -> {

            }
            FamiliesUiEvent.OnJoinFamilyClicked -> {

            }
        }
    }
}