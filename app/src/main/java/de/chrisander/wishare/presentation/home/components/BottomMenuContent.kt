package de.chrisander.wishare.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import de.chrisander.wishare.R

enum class BottomMenuContent(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
){
    Families(R.string.bottom_menu_families, R.drawable.ic_family),
    MyWishes(R.string.bottom_menu_wishes, R.drawable.ic_present),
    Gifts(R.string.bottom_menu_gifts, R.drawable.ic_list),
}
