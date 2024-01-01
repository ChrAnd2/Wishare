package de.chrisander.wishare.di

import de.chrisander.wishare.data.di.dataModule
import de.chrisander.wishare.local.di.localModule
import de.chrisander.wishare.presentation.di.previewModule
import de.chrisander.wishare.presentation.di.uiModule
import org.koin.dsl.module

val appModules = module {
    includes(
        dataModule,
        localModule,
        uiModule
    )
}

val appPreviewModules = module {
    includes(
        appModules,
        previewModule
    )
}