package de.chrisander.wishare.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.home.components.BottomMenu
import de.chrisander.wishare.presentation.home.components.BottomMenuContent
import de.chrisander.wishare.presentation.home.families.FamiliesScreen
import de.chrisander.wishare.presentation.home.my_gifts.MyGiftsScreen
import de.chrisander.wishare.presentation.home.my_wishes.MyWishesScreen
import de.chrisander.wishare.presentation.home.settings.SettingsScreen
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
    navigator: DestinationsNavigator,
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->

        }
    }

    HomeContent(
        state = state,
        navigator = navigator,
        onBottomMenuItemClicked = {
            viewModel.onEvent(HomeUiEvent.OnBottomMenuItemClicked(it))
        })
}

@Composable
fun HomeContent(
    state: HomeScreenState,
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
    onBottomMenuItemClicked: (BottomMenuContent) -> Unit = {}
){
    Column {
        when(state){
            is HomeScreenState.Families -> FamiliesScreen(
                modifier = Modifier.weight(1f),
                navigator = navigator
            )
            is HomeScreenState.MyWishes -> MyWishesScreen(
                modifier = Modifier.weight(1f),
                navigator = navigator
            )
            is HomeScreenState.MyGifts -> MyGiftsScreen(
                modifier = Modifier.weight(1f),
                navigator = navigator
            )
            HomeScreenState.Settings -> SettingsScreen(
                modifier = Modifier.weight(1f),
                navigator = navigator
            )
        }
        val bottomContent = listOf(
            BottomMenuContent.Families,
            BottomMenuContent.MyWishes,
            BottomMenuContent.MyGifts,
            BottomMenuContent.Settings
        )
        BottomMenu(
            items = bottomContent,
            initialSelectedItemIndex = when(state){
                is HomeScreenState.Families -> bottomContent.indexOf(BottomMenuContent.Families)
                is HomeScreenState.MyWishes -> bottomContent.indexOf(BottomMenuContent.MyWishes)
                is HomeScreenState.MyGifts -> bottomContent.indexOf(BottomMenuContent.MyGifts)
                HomeScreenState.Settings -> bottomContent.indexOf(BottomMenuContent.Settings)
            },
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