package de.chrisander.wishare.data.datasource

import de.chrisander.wishare.domain.model.Family
import kotlinx.coroutines.flow.Flow

interface IFamiliesDataSource {
    var families: List<Family>
    val familiesFlow: Flow<List<Family>>
}