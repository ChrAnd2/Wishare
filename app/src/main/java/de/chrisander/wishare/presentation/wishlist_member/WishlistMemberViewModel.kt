package de.chrisander.wishare.presentation.wishlist_member

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.base.BaseViewModel
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.home.my_gifts.MyGiftsUiEvent
import de.chrisander.wishare.presentation.navArgs
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class WishlistMemberViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val membersRepository: IMembersRepository
): BaseViewModel<WishlistMemberScreenState, WishlistMemberViewModelEvent, WishlistMemberUiEvent>(), KoinComponent {

    private val navArgs: WishlistMemberNavArgs = savedStateHandle.navArgs()

    override val initialScreenState by lazy {
        val member = membersRepository.getMember(navArgs.memberId)!!
        val owner = membersRepository.getAppOwner()
        WishlistMemberScreenState(
            member = member,
            ownerId = owner.id
        )
    }

    init {
        combine(
            membersRepository.members,
            membersRepository.appOwner
        ){ members, appOwner ->
            val member = members.first { it.id == navArgs.memberId }
            _state.value = WishlistMemberScreenState(
                member = member,
                ownerId = appOwner.id
            )
        }.launchIn(viewModelScope)
    }

    fun getMemberById(id: FamilyMemberId): FamilyMember{
        return membersRepository.getMember(id)!!
    }

    override fun onEvent(event: WishlistMemberUiEvent) {
        when(event){
            is WishlistMemberUiEvent.OnBoughtClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.Bought(membersRepository.getAppOwner().id)
                ))
            }
            is WishlistMemberUiEvent.OnCancelReservationClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.Open
                ))
            }
            is WishlistMemberUiEvent.OnReserveClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.Reserved(membersRepository.getAppOwner().id)
                ))
            }
            is WishlistMemberUiEvent.OnHandedOverClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.HandedOver
                ))
            }
        }
    }
}