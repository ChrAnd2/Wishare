package de.chrisander.wishare.local.di

import de.chrisander.wishare.data.datasource.IAuthenticationDataSource
import de.chrisander.wishare.data.datasource.IFamiliesDataSource
import de.chrisander.wishare.data.datasource.IMembersDataSource
import de.chrisander.wishare.local.datasourceimpl.FamiliesDataSource
import de.chrisander.wishare.local.datasourceimpl.GoogleAuthenticationDataSource
import de.chrisander.wishare.local.datasourceimpl.MembersDataSource
import de.chrisander.wishare.local.prefhelper.IPreferenceHelper
import de.chrisander.wishare.local.prefhelper.PreferenceHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single<IPreferenceHelper> { PreferenceHelper(androidApplication()) }
    single<IMembersDataSource> { MembersDataSource(get()) }
    single<IFamiliesDataSource> { FamiliesDataSource(get()) }
    single<IAuthenticationDataSource> { GoogleAuthenticationDataSource(androidApplication())}
}