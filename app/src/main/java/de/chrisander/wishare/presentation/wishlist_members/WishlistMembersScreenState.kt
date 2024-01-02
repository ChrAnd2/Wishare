package de.chrisander.wishare.presentation.wishlist_members

import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.util.FamilyMemberId

data class WishlistMembersScreenState(
    val family: Family,
    val members: List<FamilyMember>,
    val ownerId: FamilyMemberId
)
