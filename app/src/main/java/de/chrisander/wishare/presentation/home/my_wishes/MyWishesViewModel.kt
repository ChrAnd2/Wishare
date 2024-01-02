package de.chrisander.wishare.presentation.home.my_wishes

import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class MyWishesViewModel(
    private val membersRepository: IMembersRepository
): BaseViewModel<MyWishesScreenState, MyWishesViewModelEvent, MyWishesUiEvent>() {

    override val initialScreenState by lazy {
        val initialOwner = membersRepository.getAppOwner()
        if(initialOwner.wishes.isEmpty())
            MyWishesScreenState.Empty
        else
            MyWishesScreenState.MyWishesList(initialOwner.wishes)
    }

    init {
        membersRepository.appOwner.onEach { owner ->
            _state.value = if(owner.wishes.isEmpty())
                MyWishesScreenState.Empty
            else
                MyWishesScreenState.MyWishesList(owner.wishes)
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: MyWishesUiEvent) {
        when(event){
            MyWishesUiEvent.OnCreateWishClicked -> {
                viewModelScope.launch { _eventFlow.emit(MyWishesViewModelEvent.NavigateToCreateWish) }
            }
            is MyWishesUiEvent.OnEditWishClicked -> {
                viewModelScope.launch { _eventFlow.emit(MyWishesViewModelEvent.NavigateToEditWish(event.wish.id)) }
            }
        }
    }
}