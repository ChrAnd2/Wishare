package de.chrisander.wishare.data.datasource

import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.flow.Flow

interface IMembersDataSource {
    var members: List<FamilyMember>
    val membersFlow: Flow<List<FamilyMember>>
    var appOwnerId: FamilyMemberId?
    val appOwnerIdFlow: Flow<FamilyMemberId?>
}