package de.chrisander.wishare.presentation.home.my_wishes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.presentation.destinations.MyWishScreenDestination
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.di.previewModule
import de.chrisander.wishare.presentation.di.uiModule
import de.chrisander.wishare.presentation.home.my_wishes.components.EmptyMyWishesScreen
import de.chrisander.wishare.presentation.home.my_wishes.components.MyWishListItem
import de.chrisander.wishare.presentation.home.my_wishes.components.MyWishesList
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun MyWishesScreen(
    modifier: Modifier = Modifier,
    viewModel: MyWishesViewModel = koinViewModel(),
    navigator: DestinationsNavigator
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                MyWishesViewModelEvent.NavigateToCreateWish -> {
                    navigator.navigate(MyWishScreenDestination(wishId = null))
                }
                is MyWishesViewModelEvent.NavigateToEditWish -> {
                    navigator.navigate(MyWishScreenDestination(wishId = event.wishId))
                }
            }
        }
    }

    MyWishesContent(
        modifier = modifier,
        screenState = state,
        onCreateWishClicked = { viewModel.onEvent(MyWishesUiEvent.OnCreateWishClicked) },
        onEditWishClicked = { viewModel.onEvent(MyWishesUiEvent.OnEditWishClicked(it)) }
    )
}

@Composable
fun MyWishesContent(
    modifier: Modifier = Modifier,
    screenState: MyWishesScreenState,
    onCreateWishClicked: () -> Unit = {},
    onEditWishClicked: (Wish) -> Unit = {}
){
    when(screenState){
        MyWishesScreenState.Empty -> {
            EmptyMyWishesScreen(
                modifier = modifier,
                onCreateWishClicked = onCreateWishClicked
            )
        }
        is MyWishesScreenState.MyWishesList -> {
            MyWishesList(
                modifier = modifier.background(MaterialTheme.colorScheme.background).padding(8.dp),
                wishes = screenState.wishes,
                onCreateWishClicked = onCreateWishClicked,
                onEditWishClicked = onEditWishClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyWishesScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            MyWishesContent(
                screenState = MyWishesScreenState.MyWishesList(
                    wishes = koinInject<List<Wish>>(named(PreviewData.MustermannChristianWishes)).filter {
                        it.state != WishState.HandedOver
                    }
                )
            )
        }
    }
}