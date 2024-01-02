package de.chrisander.wishare.domain.repository

import com.github.michaelbull.result.Result
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.WiShareInternalError
import de.chrisander.wishare.domain.util.FamilyId
import kotlinx.coroutines.flow.StateFlow

interface IFamiliesRepository {
    val families: StateFlow<List<Family>>
    fun getFamilies(): List<Family>
    fun getFamily(familyId: FamilyId): Family?
    fun addOrUpdateFamily(family: Family): Result<Unit, WiShareInternalError>
    fun removeFamily(familyId: FamilyId): Result<Unit, WiShareInternalError>

}