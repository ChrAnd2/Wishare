package de.chrisander.wishare.presentation.family

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import de.chrisander.wishare.R
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.UiImage
import de.chrisander.wishare.domain.repository.IFamiliesRepository
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.presentation.base.BaseViewModel
import de.chrisander.wishare.presentation.navArgs
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

@OptIn(FlowPreview::class)
class FamilyViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val familiesRepository: IFamiliesRepository,
    private val membersRepository: IMembersRepository
): BaseViewModel<FamilyScreenState, FamilyViewModelEvent, FamilyUiEvent>(), KoinComponent {

    private val navArgs: FamilyScreenNavArgs = savedStateHandle.navArgs()

    private val family by lazy {
        if(navArgs.familyId != null) {
            val foundFamily = familiesRepository.getFamily(navArgs.familyId)
            if(foundFamily != null)
                return@lazy foundFamily
        }

        val newFamily = Family(
            name = "",
            image = UiImage.Static(R.drawable.ic_family),
            memberIds = listOf(membersRepository.getAppOwner().id)
        )
        familiesRepository.addOrUpdateFamily(newFamily)
        return@lazy newFamily
    }

    private val members by lazy {
        family.memberIds.map {
            membersRepository.getMember(it)
        }.filterNotNull()
    }

    override val initialScreenState = FamilyScreenState(
        family = family,
        members = members
    )

    init {
        val familyFlow = familiesRepository.families.map {
            it.firstOrNull { it.id == family.id }
        }.filterNotNull()
        val membersFlow = familyFlow.map { family ->
            membersRepository.members.map { members ->
                members.filter { member ->
                    family.memberIds.any { it == member.id }
                }
            }
        }.flattenMerge()
        combine(
            familyFlow,
            membersFlow
        ){ family, members ->
            _state.value = FamilyScreenState(
                family = family,
                members = members
            )
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: FamilyUiEvent) {
        when(event){
            is FamilyUiEvent.OnFamilyImageChanged -> {
                val newFamily = state.value.family.copy(image = event.newImage)
                familiesRepository.addOrUpdateFamily(newFamily)
            }
            is FamilyUiEvent.OnFamilyNameChanged -> {
                val newFamily = state.value.family.copy(name = event.newName)
                familiesRepository.addOrUpdateFamily(newFamily)
            }
            FamilyUiEvent.OnRemoveClicked -> {
                familiesRepository.removeFamily(family.id)
                viewModelScope.launch { _eventFlow.emit(FamilyViewModelEvent.NavigateUp) }
            }
        }
    }
}