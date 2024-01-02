package de.chrisander.wishare.presentation.home.families

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.presentation.destinations.FamilyScreenDestination
import de.chrisander.wishare.presentation.destinations.WishlistMembersScreenDestination
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.home.families.components.EmptyFamilyScreen
import de.chrisander.wishare.presentation.home.families.components.FamilyList
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun FamiliesScreen(
    modifier: Modifier = Modifier,
    viewModel: FamiliesViewModel = koinViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is FamiliesViewModelEvent.NavigateToEditFamily -> {
                    navigator.navigate(FamilyScreenDestination(familyId = event.familyId))
                }
                FamiliesViewModelEvent.NavigateToCreateFamily -> {
                    navigator.navigate(FamilyScreenDestination(familyId = null))
                }
                FamiliesViewModelEvent.NavigateToJoinFamily -> {
                    TODO()
                }
                is FamiliesViewModelEvent.NavigateToFamily -> {
                    navigator.navigate(WishlistMembersScreenDestination(event.familyId))
                }
            }
        }
    }

    FamiliesContent(
        modifier = modifier,
        screenState = state,
        onCreateFamilyClicked = { viewModel.onEvent(FamiliesUiEvent.OnCreateFamilyClicked) },
        onJoinFamilyClicked = { viewModel.onEvent(FamiliesUiEvent.OnJoinFamilyClicked) },
        onFamilyClicked = { viewModel.onEvent(FamiliesUiEvent.OnFamilyClicked(it)) },
        onEditFamilyClicked = { viewModel.onEvent(FamiliesUiEvent.OnEditFamilyClicked(it)) },
    )
}

@Composable
fun FamiliesContent(
    modifier: Modifier = Modifier,
    screenState: FamiliesScreenState,
    onCreateFamilyClicked: () -> Unit = {},
    onJoinFamilyClicked: () -> Unit = {},
    onFamilyClicked: (Family) -> Unit = {},
    onEditFamilyClicked: (Family) -> Unit = {},
){
    when(screenState){
        FamiliesScreenState.Empty -> {
            EmptyFamilyScreen(
                modifier = modifier,
                onCreateFamilyClicked = onCreateFamilyClicked,
                onJoinFamilyClicked = onJoinFamilyClicked,
            )
        }
        is FamiliesScreenState.FamiliesList -> {
            FamilyList(
                modifier = modifier,
                families = screenState.families,
                onFamilyClicked = onFamilyClicked,
                onEditFamilyClicked = onEditFamilyClicked,
                onAddFamilyClicked = onCreateFamilyClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FamiliesScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            FamiliesContent(
                screenState = FamiliesScreenState.FamiliesList(
                    families = koinInject(named(PreviewData.Families))
                )
            )
        }
    }
}