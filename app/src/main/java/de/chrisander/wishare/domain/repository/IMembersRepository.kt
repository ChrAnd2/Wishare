package de.chrisander.wishare.domain.repository

import com.github.michaelbull.result.Result
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.WiShareInternalError
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.flow.StateFlow

interface IMembersRepository {

    val members: StateFlow<List<FamilyMember>>
    val appOwner: StateFlow<FamilyMember>

    fun getMembers(): List<FamilyMember>
    fun getMember(memberId: FamilyMemberId): FamilyMember?
    fun getAppOwner(): FamilyMember
    fun addOrUpdateMember(member: FamilyMember): Result<Unit, WiShareInternalError>
    fun addOrUpdateWish(wish: Wish): Result<Unit, WiShareInternalError>
    fun removeWish(wish: Wish): Result<Unit, WiShareInternalError>
}