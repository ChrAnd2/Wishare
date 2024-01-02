package de.chrisander.wishare.presentation.home.my_gifts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.home.my_gifts.components.EmptyMyGiftsScreen
import de.chrisander.wishare.presentation.home.my_gifts.components.MyGiftsList
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun MyGiftsScreen(
    modifier: Modifier = Modifier,
    viewModel: MyGiftsViewModel = koinViewModel(),
    navigator: DestinationsNavigator
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->

        }
    }

    MyGiftsContent(
        modifier = modifier,
        screenState = state,
        getMemberById = viewModel::getMemberById,
        onReserveClicked = { viewModel.onEvent(MyGiftsUiEvent.OnReserveClicked(it)) },
        onBoughtClicked = { viewModel.onEvent(MyGiftsUiEvent.OnBoughtClicked(it)) },
        onHandedOverClicked = { viewModel.onEvent(MyGiftsUiEvent.OnHandedOverClicked(it)) },
        onCancelReservationClicked = { viewModel.onEvent(MyGiftsUiEvent.OnCancelReservationClicked(it)) }
    )

}

@Composable
fun MyGiftsContent(
    modifier: Modifier = Modifier,
    screenState: MyGiftsScreenState,
    getMemberById: (FamilyMemberId) -> FamilyMember,
    onReserveClicked: (Wish) -> Unit = {},
    onBoughtClicked: (Wish) -> Unit = {},
    onHandedOverClicked: (Wish) -> Unit = {},
    onCancelReservationClicked: (Wish) -> Unit = {},
) {
    when(screenState){
        MyGiftsScreenState.Empty -> EmptyMyGiftsScreen(modifier)
        is MyGiftsScreenState.GiftsList -> {
            MyGiftsList(
                modifier = modifier,
                gifts = screenState.gifts,
                ownerId = screenState.ownerId,
                getMemberById = getMemberById,
                onReserveClicked = onReserveClicked,
                onBoughtClicked = onBoughtClicked,
                onHandedOverClicked = onHandedOverClicked,
                onCancelReservationClicked = onCancelReservationClicked,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyGiftsScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        val previewMember: FamilyMember = koinInject(named(PreviewData.MustermannChristianMember))
        WishareTheme {
            MyGiftsContent(
                screenState = MyGiftsScreenState.GiftsList(
                    gifts = koinInject(named(PreviewData.MustermannSimonWishes)),
                    ownerId = koinInject(named(PreviewData.MustermannChristianId)),
                ),
                getMemberById = { previewMember }
            )
        }
    }
}