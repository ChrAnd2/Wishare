package de.chrisander.wishare.local.datasourceimpl

import de.chrisander.wishare.data.datasource.IMembersDataSource
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.local.prefhelper.IPreferenceHelper
import kotlinx.coroutines.flow.Flow

class MembersDataSource(
    private val prefHelper: IPreferenceHelper
): IMembersDataSource {
    override var members: List<FamilyMember> by prefHelper::members
    override val membersFlow: Flow<List<FamilyMember>> = prefHelper.membersFlow
    override val appOwnerId: FamilyMemberId? by prefHelper::appOwnerId
    override val appOwnerIdFlow: Flow<FamilyMemberId?> = prefHelper.appOwnerIdFlow
}