package de.chrisander.wishare.presentation.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel<UiState, ViewModelEvent, UiEvent>: ViewModel() {
    abstract val initialScreenState: UiState

    protected val _state: MutableState<UiState> by lazy { mutableStateOf(initialScreenState) }
    val state: State<UiState> by lazy { _state }


    protected val _eventFlow = MutableSharedFlow<ViewModelEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    abstract fun onEvent(event: UiEvent)
}