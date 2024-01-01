package de.chrisander.wishare

import de.chrisander.wishare.di.appModules
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.di.previewModule
import de.chrisander.wishare.presentation.di.uiModule
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.verify.verify
import java.util.UUID

class KoinTest: KoinComponent {

    @Test
    fun checkKoinModule() {
        // Verify Koin configuration
        appModules.verify()
    }

    @Test
    fun checkKoinPreviewModule() {

        // Verify Koin configuration
        appPreviewModules.verify(
            extraTypes = listOf(
                java.net.URL::class,
                WishState::class,
            )
        )
    }
}