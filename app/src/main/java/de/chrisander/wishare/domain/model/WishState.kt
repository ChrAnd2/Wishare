package de.chrisander.wishare.domain.model

import androidx.compose.ui.graphics.Color
import de.chrisander.wishare.R
import de.chrisander.wishare.ui.theme.wishStateBoughtColor
import de.chrisander.wishare.ui.theme.wishStateHandedOverColor
import de.chrisander.wishare.ui.theme.wishStateOpenColor
import de.chrisander.wishare.ui.theme.wishStateReservedColor

sealed class WishState {
    abstract val nameRes: Int
    abstract val color: Color

    interface HasByMember {
        val byMember: FamilyMember
    }

    data object Open: WishState(){
        override val nameRes = R.string.wish_state_open
        override val color = wishStateOpenColor
    }
    data class Reserved(override val byMember: FamilyMember): WishState(), HasByMember{
        override val nameRes = R.string.wish_state_reserved
        override val color = wishStateReservedColor
    }
    data class Bought(override val byMember: FamilyMember): WishState(), HasByMember{
        override val nameRes = R.string.wish_state_bought
        override val color = wishStateBoughtColor
    }
    data object HandedOver: WishState(){
        override val nameRes = R.string.wish_state_handed_over
        override val color = wishStateHandedOverColor
    }
}
