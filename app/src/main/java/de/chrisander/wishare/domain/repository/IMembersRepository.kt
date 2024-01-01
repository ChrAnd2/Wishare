package de.chrisander.wishare.domain.repository

import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.flow.StateFlow

interface IMembersRepository {

    val members: StateFlow<List<FamilyMember>>
    val appOwner: StateFlow<FamilyMember>

    fun getMembers(): List<FamilyMember>
    fun getMember(memberId: FamilyMemberId): FamilyMember?
    fun getAppOwner(): FamilyMember
}