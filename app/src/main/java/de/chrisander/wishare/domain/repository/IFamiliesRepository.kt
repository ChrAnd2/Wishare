package de.chrisander.wishare.domain.repository

import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IFamiliesRepository {
    val families: StateFlow<List<Family>>

    fun getFamilies(): List<Family>
}