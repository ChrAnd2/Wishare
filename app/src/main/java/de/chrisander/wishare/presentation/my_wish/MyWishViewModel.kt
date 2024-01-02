package de.chrisander.wishare.presentation.my_wish

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Result
import de.chrisander.wishare.domain.model.WiShareInternalError
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.domain.util.WishId
import de.chrisander.wishare.presentation.base.BaseViewModel
import de.chrisander.wishare.presentation.navArgs
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.URL

class MyWishViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val membersRepository: IMembersRepository
): BaseViewModel<MyWishScreenState, MyWishViewModelEvent, MyWishUiEvent>() {

    private val navArgs: MyWishNavArgs = savedStateHandle.navArgs()

    private val wish by lazy {
        if(navArgs.wishId != null) {
            val navWish = membersRepository.getAppOwner().wishes.firstOrNull { it.id == navArgs.wishId }
            if(navWish != null)
                return@lazy navWish
        }

        val newWish = Wish(
            name = "",
            description = "",
            creationDate = System.currentTimeMillis(),
            url = null,
            state = WishState.Open,
            wisherId = membersRepository.getAppOwner().id
        )
        membersRepository.addOrUpdateWish(newWish)
        newWish
    }

    override val initialScreenState = MyWishScreenState(wish = wish)

    init {
        membersRepository.appOwner.map { member ->
            member.wishes.firstOrNull { it.id == wish.id }
        }.filterNotNull().onEach {
            _state.value = MyWishScreenState(it)
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: MyWishUiEvent) {
        when(event){
            is MyWishUiEvent.OnWishDescriptionChanged -> {
                val newWish = state.value.wish.copy(
                    description = event.newDescription
                )
                membersRepository.addOrUpdateWish(newWish)
            }
            is MyWishUiEvent.OnWishTitleChanged -> {
                val newWish = state.value.wish.copy(
                    name = event.newTitle
                )
                membersRepository.addOrUpdateWish(newWish)
            }
            is MyWishUiEvent.OnWishUrlChanged -> {
                val newWish = state.value.wish.copy(
                    url = URL(event.newUrl)
                )
                membersRepository.addOrUpdateWish(newWish)
            }
            MyWishUiEvent.OnWishRemoveClicked -> {
                membersRepository.removeWish(wish)
                viewModelScope.launch {
                    _eventFlow.emit(MyWishViewModelEvent.NavigateUp)
                }
            }
        }
    }
}