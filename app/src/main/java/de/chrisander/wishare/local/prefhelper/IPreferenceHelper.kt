package de.chrisander.wishare.local.prefhelper

import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.flow.Flow

interface IPreferenceHelper{
    var families: List<Family>
    val familiesFlow: Flow<List<Family>>

    var members: List<FamilyMember>
    val membersFlow: Flow<List<FamilyMember>>

    var appOwnerId: FamilyMemberId?
    val appOwnerIdFlow: Flow<FamilyMemberId?>
}