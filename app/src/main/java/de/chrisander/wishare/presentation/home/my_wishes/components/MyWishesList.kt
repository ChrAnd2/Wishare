package de.chrisander.wishare.presentation.home.my_wishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun MyWishesList(
    modifier: Modifier = Modifier,
    wishes: List<Wish>,
    onEditWishClicked: (Wish) -> Unit = {}
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        wishes.forEach {
            MyWishListItem(
                modifier = Modifier.padding(8.dp),
                myWish = it,
                onEditWishClicked = onEditWishClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyWishesListPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            MyWishesList(
                wishes = koinInject(named(PreviewData.MustermannChristianWishes))
            )
        }
    }
}