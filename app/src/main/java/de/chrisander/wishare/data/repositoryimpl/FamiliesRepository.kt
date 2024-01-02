package de.chrisander.wishare.data.repositoryimpl

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import de.chrisander.wishare.data.datasource.IFamiliesDataSource
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.WiShareInternalError
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.repository.IFamiliesRepository
import de.chrisander.wishare.domain.util.FamilyId
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
    override fun getFamily(familyId: FamilyId): Family? {
        return getFamilies().firstOrNull { it.id == familyId }
    }

    override fun addOrUpdateFamily(family: Family): Result<Unit, WiShareInternalError> {
        _families.value = if(getFamilies().any { it.id == family.id})
            getFamilies().map {
                if(it.id == family.id)
                    family
                else
                    it
            }
        else
            getFamilies().plus(family)
        return Ok(Unit)
    }

    override fun removeFamily(familyId: FamilyId): Result<Unit, WiShareInternalError> {
        _families.value = getFamilies().filter { it.id != familyId }
        return Ok(Unit)
    }
}