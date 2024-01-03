package de.chrisander.wishare.presentation.home

import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.domain.repository.IFamiliesRepository
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.presentation.base.BaseViewModel
import de.chrisander.wishare.presentation.home.components.BottomMenuContent
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import org.koin.core.component.KoinComponent

class HomeViewModel(
    private val familiesRepository: IFamiliesRepository,
    private val membersRepository: IMembersRepository
): BaseViewModel<HomeScreenState, HomeViewModelEvent, HomeUiEvent>(), KoinComponent {

    override val initialScreenState = HomeScreenState.Families(
        families = familiesRepository.getFamilies()
    )

    init {
        combine(
            familiesRepository.families,
            membersRepository.members,
            membersRepository.appOwner
        ){ families, members, appOwner ->
            when(state.value) {
                is HomeScreenState.Families -> {
                    HomeScreenState.Families(families = families)
                }
                is HomeScreenState.MyGifts -> {
                    HomeScreenState.MyGifts(
                        myGifts = members.flatMap {
                            it.wishes.filter {
                                it.state is WishState.HasByMember && it.state.byMemberId == appOwner.id
                            }
                        }
                    )
                }
                is HomeScreenState.MyWishes -> {
                    HomeScreenState.MyWishes(myWishes = appOwner.wishes)
                }
                HomeScreenState.Settings -> HomeScreenState.Settings
            }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: HomeUiEvent) {
        when(event){
            is HomeUiEvent.OnBottomMenuItemClicked -> {
                when(event.bottomMenuContent){
                    BottomMenuContent.Families -> _state.value = HomeScreenState.Families(
                        families = familiesRepository.getFamilies()
                    )
                    BottomMenuContent.MyWishes -> _state.value = HomeScreenState.MyWishes(
                        myWishes = membersRepository.getAppOwner().wishes
                    )
                    BottomMenuContent.MyGifts -> _state.value = HomeScreenState.MyGifts(
                        myGifts = membersRepository.getMembers().flatMap { member ->
                            member.wishes.filter { wish ->
                                wish.state is WishState.HasByMember &&
                                wish.state.byMemberId == membersRepository.getAppOwner().id
                            }
                        }
                    )
                    BottomMenuContent.Settings -> _state.value = HomeScreenState.Settings
                }
            }
        }
    }
}