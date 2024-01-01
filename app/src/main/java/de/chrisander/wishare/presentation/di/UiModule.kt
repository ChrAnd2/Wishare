package de.chrisander.wishare.presentation.di

import de.chrisander.wishare.presentation.home.HomeViewModel
import de.chrisander.wishare.presentation.home.families.FamiliesViewModel
import de.chrisander.wishare.presentation.home.my_gifts.MyGiftsViewModel
import de.chrisander.wishare.presentation.home.my_wishes.MyWishesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val uiModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::FamiliesViewModel)
    viewModelOf(::MyGiftsViewModel)
    viewModelOf(::MyWishesViewModel)
}