package de.chrisander.wishare.presentation.wishlist_members

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.repository.IFamiliesRepository
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.presentation.base.BaseViewModel
import de.chrisander.wishare.presentation.navArgs
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class WishlistMembersViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val familiesRepository: IFamiliesRepository,
    private val membersRepository: IMembersRepository
) : BaseViewModel<WishlistMembersScreenState, WishlistMembersViewModelEvent, WishlistMembersUiEvent>(),
    KoinComponent {

    private val navArgs: WishlistMembersNavArgs = savedStateHandle.navArgs()

    override val initialScreenState by lazy {
        val family: Family = familiesRepository.getFamily(navArgs.familyId)!!
        val members = family.memberIds.map {
            membersRepository.getMember(it)
        }.filterNotNull()
        val ownerId = membersRepository.getAppOwner().id
        WishlistMembersScreenState(
            family = family,
            members = members,
            ownerId = ownerId
        )
    }

    init {
        combine(
            familiesRepository.families,
            membersRepository.members,
            membersRepository.appOwner
        ) { families, members, appOwner ->
            val family: Family = families.first { it.id == navArgs.familyId }
            val familyMembers = members.filter { it.id in family.memberIds }
            _state.value = WishlistMembersScreenState(
                family = family,
                members = familyMembers,
                ownerId = appOwner.id
            )
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: WishlistMembersUiEvent) {
        when (event) {
            is WishlistMembersUiEvent.OnMemberClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(WishlistMembersViewModelEvent.NavigateToMemberWishlist(event.member.id))
                }
            }
        }
    }
}