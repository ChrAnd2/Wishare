package de.chrisander.wishare.presentation.home.families

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.Family
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
    viewModel: FamiliesViewModel = koinViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is FamiliesViewModelEvent.NavigateToEditFamily -> TODO()
                FamiliesViewModelEvent.NavigateToCreateFamily -> TODO()
                FamiliesViewModelEvent.NavigateToJoinFamily -> TODO()
            }
        }
    }

    FamiliesContent(
        modifier = modifier,
        screenState = state,
        onCreateFamilyClicked = { viewModel.onEvent(FamiliesUiEvent.OnCreateFamilyClicked) },
        onJoinFamilyClicked = { viewModel.onEvent(FamiliesUiEvent.OnJoinFamilyClicked) },
        onClicked = { viewModel.onEvent(FamiliesUiEvent.OnFamilyClicked(it)) },
        onEditClicked = { viewModel.onEvent(FamiliesUiEvent.OnEditFamilyClicked(it)) }
    )
}

@Composable
fun FamiliesContent(
    modifier: Modifier = Modifier,
    screenState: FamiliesScreenState,
    onCreateFamilyClicked: () -> Unit = {},
    onJoinFamilyClicked: () -> Unit = {},
    onClicked: (Family) -> Unit = {},
    onEditClicked: (Family) -> Unit = {},
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
                onClicked = onClicked,
                onEditClicked = onEditClicked,
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