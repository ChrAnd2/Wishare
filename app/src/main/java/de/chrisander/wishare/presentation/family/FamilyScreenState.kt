package de.chrisander.wishare.presentation.family

import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember

data class FamilyScreenState(
    val family: Family,
    val members: List<FamilyMember>
)
