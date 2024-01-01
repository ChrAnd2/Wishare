package de.chrisander.wishare.data.di

import de.chrisander.wishare.data.repositoryimpl.FamiliesRepository
import de.chrisander.wishare.data.repositoryimpl.MembersRepository
import de.chrisander.wishare.domain.repository.IFamiliesRepository
import de.chrisander.wishare.domain.repository.IMembersRepository
import org.koin.dsl.module

val dataModule = module {
    single<IFamiliesRepository> { FamiliesRepository(get()) }
    single<IMembersRepository> { MembersRepository(get()) }
}