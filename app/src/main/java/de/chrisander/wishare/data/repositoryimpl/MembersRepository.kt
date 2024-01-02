package de.chrisander.wishare.data.repositoryimpl

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import de.chrisander.wishare.data.datasource.IMembersDataSource
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.WiShareInternalError
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MembersRepository(
    private val localMembersDataSource: IMembersDataSource
): IMembersRepository {

    private val _members = MutableStateFlow(localMembersDataSource.members)
    override val members = _members.asStateFlow()

    //TODO(Implement real App Owner logic)
    private val newAppOwner = FamilyMember(
        name = "Christian",
        wishes = emptyList()
    )

    override val appOwner: StateFlow<FamilyMember> = combine(
        members,
        localMembersDataSource.appOwnerIdFlow
    ) { members, appOwnerId ->
        val owner = members.firstOrNull {
            it.id == appOwnerId
        } ?: newAppOwner
        if(appOwnerId != owner.id) {
            localMembersDataSource.appOwnerId = owner.id
            _members.value = getMembers().plus(newAppOwner)
        }
        owner
    }.stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, getAppOwner())

    override fun getMembers(): List<FamilyMember> = members.value

    override fun getMember(memberId: FamilyMemberId): FamilyMember? = members.value.firstOrNull {
        it.id == memberId
    }

    override fun getAppOwner(): FamilyMember {
        val owner = getMembers().firstOrNull {
            it.id == localMembersDataSource.appOwnerId
        } ?: newAppOwner
        if(localMembersDataSource.appOwnerId != owner.id) {
            localMembersDataSource.appOwnerId = owner.id
            _members.value = getMembers().plus(newAppOwner)
        }
        return owner
    }

    override fun addOrUpdateMember(member: FamilyMember): Result<Unit, WiShareInternalError> {
        _members.value = if(getMember(member.id) == null)
            getMembers().plus(member)
        else
            getMembers().map {
                if(it.id == member.id)
                    member
                else
                    it
            }
        return Ok(Unit)
    }

    override fun addOrUpdateWish(wish: Wish): Result<Unit, WiShareInternalError> {
        val currentWisher = getMember(wish.wisherId)
            ?: return Err(WiShareInternalError.RuntimeModelError.CouldNotFindMember(wish.wisherId))
        return addOrUpdateMember(currentWisher.copy(
            wishes = currentWisher.wishes.filter {
                it.id != wish.id
            }.plus(wish)
        ))
    }

    override fun removeWish(wish: Wish): Result<Unit, WiShareInternalError> {
        val currentWisher = getMember(wish.wisherId)
            ?: return Err(WiShareInternalError.RuntimeModelError.CouldNotFindMember(wish.wisherId))
        return addOrUpdateMember(currentWisher.copy(
            wishes = currentWisher.wishes.filter {
                it.id != wish.id
            }
        ))
    }
}