package de.chrisander.wishare

import android.app.Application
import de.chrisander.wishare.di.appModules
import de.chrisander.wishare.presentation.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }
}