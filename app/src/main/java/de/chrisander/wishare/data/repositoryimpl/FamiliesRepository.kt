package de.chrisander.wishare.data.repositoryimpl

import de.chrisander.wishare.data.datasource.IFamiliesDataSource
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.repository.IFamiliesRepository
import de.chrisander.wishare.domain.util.FamilyMemberId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FamiliesRepository(
    private val localFamiliesDataSource: IFamiliesDataSource
): IFamiliesRepository {

    private val _families = MutableStateFlow(localFamiliesDataSource.families)
    override val families: StateFlow<List<Family>> = _families.asStateFlow()

    override fun getFamilies(): List<Family> = families.value
}