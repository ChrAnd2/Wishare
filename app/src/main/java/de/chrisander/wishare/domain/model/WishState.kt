package de.chrisander.wishare.domain.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.squareup.moshi.JsonClass
import de.chrisander.wishare.R
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.ui.theme.wishStateBoughtColor
import de.chrisander.wishare.ui.theme.wishStateHandedOverColor
import de.chrisander.wishare.ui.theme.wishStateOpenColor
import de.chrisander.wishare.ui.theme.wishStateReservedColor
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.UUID

sealed class WishState: Parcelable {
    abstract val nameRes: Int
    abstract val color: Color

    interface HasByMember {
        val byMemberId: FamilyMemberId
    }

    @Parcelize
    data object Open: WishState(){
        @IgnoredOnParcel
        override val nameRes = R.string.wish_state_open
        @IgnoredOnParcel
        override val color = wishStateOpenColor
    }

    @Parcelize
    data class Reserved(override val byMemberId: FamilyMemberId): WishState(), HasByMember{
        @IgnoredOnParcel
        override val nameRes = R.string.wish_state_reserved
        @IgnoredOnParcel
        override val color = wishStateReservedColor
    }

    @Parcelize
    data class Bought(override val byMemberId: FamilyMemberId): WishState(), HasByMember{
        @IgnoredOnParcel
        override val nameRes = R.string.wish_state_bought
        @IgnoredOnParcel
        override val color = wishStateBoughtColor
    }

    @Parcelize
    data object HandedOver: WishState(){
        @IgnoredOnParcel
        override val nameRes = R.string.wish_state_handed_over
        @IgnoredOnParcel
        override val color = wishStateHandedOverColor
    }
}
