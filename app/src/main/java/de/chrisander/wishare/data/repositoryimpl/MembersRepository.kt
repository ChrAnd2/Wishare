package de.chrisander.wishare.data.repositoryimpl

import de.chrisander.wishare.data.datasource.IMembersDataSource
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.repository.IMembersRepository
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MembersRepository(
    private val localMembersDataSource: IMembersDataSource
): IMembersRepository {

    private val _members = MutableStateFlow(localMembersDataSource.members)
    override val members = _members.asStateFlow()

    override val appOwner: StateFlow<FamilyMember> = members.map {
        it.firstOrNull {
            it.id == localMembersDataSource.appOwnerId
        } ?: FamilyMember(
            name = "Christian",
            wishes = emptyList()
        )
    }.stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, getAppOwner())

    override fun getMembers(): List<FamilyMember> = members.value

    override fun getMember(memberId: FamilyMemberId): FamilyMember? = members.value.firstOrNull {
        it.id == memberId
    }

    override fun getAppOwner(): FamilyMember {
        return getMembers().firstOrNull {
            it.id == localMembersDataSource.appOwnerId
        } ?: FamilyMember(
            name = "Christian",
            wishes = emptyList()
        )
    }
}