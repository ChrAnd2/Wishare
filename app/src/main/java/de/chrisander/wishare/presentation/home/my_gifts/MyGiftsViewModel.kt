package de.chrisander.wishare.presentation.home.my_gifts

import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import org.koin.core.component.KoinComponent

class MyGiftsViewModel(
    private val membersRepository: IMembersRepository
): BaseViewModel<MyGiftsScreenState, MyGiftsViewModelEvent, MyGiftsUiEvent>(), KoinComponent {
    override val initialScreenState by lazy {
        val allWishes = membersRepository.getMembers().flatMap { it.wishes }
        val appOwner = membersRepository.getAppOwner()
        val myGifts = allWishes.filter {
            it.state is WishState.HasByMember && it.state.byMemberId == appOwner.id
        }
        if(myGifts.isEmpty())
            MyGiftsScreenState.Empty
        else
            MyGiftsScreenState.GiftsList(
                gifts = myGifts,
                ownerId = appOwner.id
            )
    }

    init {
        combine(
            membersRepository.members,
            membersRepository.appOwner
        ){ members, appOwner ->
            val myGifts = members.flatMap { it.wishes }.filter {
                it.state is WishState.HasByMember && it.state.byMemberId == appOwner.id
            }
            _state.value = if(myGifts.isEmpty())
                MyGiftsScreenState.Empty
            else
                MyGiftsScreenState.GiftsList(
                    gifts = myGifts,
                    ownerId = appOwner.id
                )
        }.launchIn(viewModelScope)
    }


    fun getMemberById(id: FamilyMemberId): FamilyMember{
        return membersRepository.getMember(id)!!
    }

    override fun onEvent(event: MyGiftsUiEvent) {
        when(event){
            is MyGiftsUiEvent.OnBoughtClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.Bought(membersRepository.getAppOwner().id)
                ))
            }
            is MyGiftsUiEvent.OnCancelReservationClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.Open
                ))
            }
            is MyGiftsUiEvent.OnReserveClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.Reserved(membersRepository.getAppOwner().id)
                ))
            }
            is MyGiftsUiEvent.OnHandedOverClicked -> {
                membersRepository.addOrUpdateWish(event.wish.copy(
                    state = WishState.HandedOver
                ))
            }
        }
    }
}