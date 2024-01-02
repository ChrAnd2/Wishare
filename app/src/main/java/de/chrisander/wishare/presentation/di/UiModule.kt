package de.chrisander.wishare.presentation.di

import de.chrisander.wishare.presentation.family.FamilyViewModel
import de.chrisander.wishare.presentation.home.HomeViewModel
import de.chrisander.wishare.presentation.home.families.FamiliesViewModel
import de.chrisander.wishare.presentation.home.my_gifts.MyGiftsViewModel
import de.chrisander.wishare.presentation.home.my_wishes.MyWishesViewModel
import de.chrisander.wishare.presentation.my_wish.MyWishViewModel
import de.chrisander.wishare.presentation.wishlist_member.WishlistMemberViewModel
import de.chrisander.wishare.presentation.wishlist_members.WishlistMembersViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val uiModule = module {
    viewModelOf(::FamilyViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::FamiliesViewModel)
    viewModelOf(::MyGiftsViewModel)
    viewModelOf(::MyWishesViewModel)
    viewModelOf(::MyWishViewModel)
    viewModelOf(::WishlistMemberViewModel)
    viewModelOf(::WishlistMembersViewModel)
}