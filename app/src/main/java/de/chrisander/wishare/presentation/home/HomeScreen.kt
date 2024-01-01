package de.chrisander.wishare.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.di.previewModule
import de.chrisander.wishare.presentation.di.uiModule
import de.chrisander.wishare.presentation.home.components.BottomMenu
import de.chrisander.wishare.presentation.home.components.BottomMenuContent
import de.chrisander.wishare.presentation.home.families.FamiliesScreen
import de.chrisander.wishare.presentation.home.my_gifts.MyGiftsScreen
import de.chrisander.wishare.presentation.home.my_wishes.MyWishesScreen
import de.chrisander.wishare.presentation.navigation.HomeNavGraph
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@HomeNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeViewModelEvent.NavigateToEditFamily -> TODO()
                HomeViewModelEvent.NavigateToCreateFamily -> TODO()
                HomeViewModelEvent.NavigateToJoinFamily -> TODO()
            }
        }
    }

    HomeContent(
        state = state,
        onBottomMenuItemClicked = {
            viewModel.onEvent(HomeUiEvent.OnBottomMenuItemClicked(it))
        })
}

@Composable
fun HomeContent(
    state: HomeScreenState,
    onBottomMenuItemClicked: (BottomMenuContent) -> Unit = {}
){
    Column {
        when(state){
            is HomeScreenState.Families -> FamiliesScreen(modifier = Modifier.weight(1f))
            is HomeScreenState.MyGifts -> MyWishesScreen(modifier = Modifier.weight(1f))
            is HomeScreenState.MyWishes -> MyGiftsScreen(modifier = Modifier.weight(1f))
        }
        BottomMenu(
            items = listOf(
                BottomMenuContent.Families,
                BottomMenuContent.MyWishes,
                BottomMenuContent.Gifts,
            ),
            onItemClick = onBottomMenuItemClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            HomeContent(
                state = HomeScreenState.Families(
                    families = koinInject(named(PreviewData.Families))
                )
            )
        }
    }
}