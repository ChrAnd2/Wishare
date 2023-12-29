package de.chrisander.wishare

import android.app.Application
import de.chrisander.wishare.presentation.di.uiModule
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(uiModule)
        }
    }
}