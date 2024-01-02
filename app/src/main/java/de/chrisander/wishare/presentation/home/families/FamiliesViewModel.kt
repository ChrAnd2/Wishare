package de.chrisander.wishare.presentation.home.families

import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.data.repositoryimpl.FamiliesRepository
import de.chrisander.wishare.domain.repository.IFamiliesRepository
import de.chrisander.wishare.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FamiliesViewModel(
    private val familiesRepository: IFamiliesRepository
): BaseViewModel<FamiliesScreenState, FamiliesViewModelEvent, FamiliesUiEvent>(), KoinComponent {

    override val initialScreenState = FamiliesScreenState.Empty

    init {
        familiesRepository.families.onEach {
            _state.value = if(it.isEmpty())
                FamiliesScreenState.Empty
            else
                FamiliesScreenState.FamiliesList(it)
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: FamiliesUiEvent) {
        when(event){
            is FamiliesUiEvent.OnFamilyClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(FamiliesViewModelEvent.NavigateToFamily(event.family.id))
                }
            }
            is FamiliesUiEvent.OnEditFamilyClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(FamiliesViewModelEvent.NavigateToEditFamily(event.family.id))
                }
            }
            FamiliesUiEvent.OnCreateFamilyClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(FamiliesViewModelEvent.NavigateToCreateFamily)
                }
            }
            FamiliesUiEvent.OnJoinFamilyClicked -> {

            }
        }
    }
}