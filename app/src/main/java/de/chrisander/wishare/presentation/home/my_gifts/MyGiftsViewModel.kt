package de.chrisander.wishare.presentation.home.my_gifts

import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.base.BaseViewModel
import org.koin.core.component.KoinComponent

class MyGiftsViewModel(
    private val membersRepository: IMembersRepository
): BaseViewModel<MyGiftsScreenState, MyGiftsViewModelEvent, MyGiftsUiEvent>(), KoinComponent {
    override val initialScreenState = MyGiftsScreenState.Empty

    fun getMemberById(id: FamilyMemberId): FamilyMember{
        return membersRepository.getMember(id)!!
    }

    override fun onEvent(event: MyGiftsUiEvent) {
        when(event){
            is MyGiftsUiEvent.OnBoughtClicked -> TODO()
            is MyGiftsUiEvent.OnCancelReservationClicked -> TODO()
            is MyGiftsUiEvent.OnReserveClicked -> TODO()
        }
    }
}