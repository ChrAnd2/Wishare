package de.chrisander.wishare.presentation.home

import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.Wish

sealed class HomeScreenState {
    data class Families(val families: List<Family>): HomeScreenState()
    data class MyWishes(val myWishes: List<Wish>): HomeScreenState()
    data class MyGifts(val myGifts: List<Wish>): HomeScreenState()
}