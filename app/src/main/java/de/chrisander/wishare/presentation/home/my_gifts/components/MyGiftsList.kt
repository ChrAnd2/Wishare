package de.chrisander.wishare.presentation.home.my_gifts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.shared.WishListItem
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun MyGiftsList(
    modifier: Modifier = Modifier,
    gifts: List<Wish>,
    ownerId: FamilyMemberId,
    getMemberById: (FamilyMemberId) -> FamilyMember,
    onReserveClicked: (Wish) -> Unit = {},
    onBoughtClicked: (Wish) -> Unit = {},
    onCancelReservationClicked: (Wish) -> Unit = {},
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ){
        gifts.forEach {
            WishListItem(
                wish = it,
                ownerId = ownerId,
                getMemberById = getMemberById,
                onReserveClicked = onReserveClicked,
                onBoughtClicked = onBoughtClicked,
                onCancelReservationClicked = onCancelReservationClicked,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyGiftsListPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        val previewMember: FamilyMember = koinInject(named(PreviewData.MustermannChristianMember))
        WishareTheme {
            MyGiftsList(
                gifts = koinInject(named(PreviewData.MustermannSimonWishes)),
                ownerId = koinInject(named(PreviewData.MustermannChristianId)),
                getMemberById = { previewMember }
            )
        }
    }
}