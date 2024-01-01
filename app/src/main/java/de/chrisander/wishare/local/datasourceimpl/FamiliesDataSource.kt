package de.chrisander.wishare.local.datasourceimpl

import de.chrisander.wishare.data.datasource.IFamiliesDataSource
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.local.prefhelper.IPreferenceHelper
import kotlinx.coroutines.flow.Flow

class FamiliesDataSource(
    private val prefHelper: IPreferenceHelper
): IFamiliesDataSource {
    override var families: List<Family> by prefHelper::families
    override val familiesFlow: Flow<List<Family>> = prefHelper.familiesFlow
}