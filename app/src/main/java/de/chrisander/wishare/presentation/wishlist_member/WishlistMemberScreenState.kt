package de.chrisander.wishare.presentation.wishlist_member

import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.util.FamilyMemberId

data class WishlistMemberScreenState(
    val member: FamilyMember,
    val ownerId: FamilyMemberId
)
