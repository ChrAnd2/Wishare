package de.chrisander.wishare.presentation.wishlist_member

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.di.previewModule
import de.chrisander.wishare.presentation.di.uiModule
import de.chrisander.wishare.presentation.home.my_gifts.MyGiftsUiEvent
import de.chrisander.wishare.presentation.navigation.HomeNavGraph
import de.chrisander.wishare.presentation.shared.WishListItem
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@HomeNavGraph
@Destination(
    navArgsDelegate = WishlistMemberNavArgs::class
)
@Composable
fun WishlistMemberScreen(
    viewModel: WishlistMemberViewModel = koinViewModel()
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->

        }
    }

    WishlistMemberContent(
        member = state.member,
        ownerId = state.ownerId,
        getMemberById = viewModel::getMemberById,
        onReserveClicked = { viewModel.onEvent(WishlistMemberUiEvent.OnReserveClicked(it)) },
        onBoughtClicked = { viewModel.onEvent(WishlistMemberUiEvent.OnBoughtClicked(it)) },
        onHandedOverClicked = { viewModel.onEvent(WishlistMemberUiEvent.OnHandedOverClicked(it)) },
        onCancelReservationClicked = { viewModel.onEvent(WishlistMemberUiEvent.OnCancelReservationClicked(it)) }
    )
}

@Composable
fun WishlistMemberContent(
    member: FamilyMember,
    ownerId: FamilyMemberId,
    getMemberById: (FamilyMemberId) -> FamilyMember,
    onReserveClicked: (Wish) -> Unit = {},
    onBoughtClicked: (Wish) -> Unit = {},
    onHandedOverClicked: (Wish) -> Unit = {},
    onCancelReservationClicked: (Wish) -> Unit = {},
){
    val sortedWishes = remember(member.wishes) {
        member.wishes.sortedBy { it.creationDate }
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        sortedWishes.forEach {
            WishListItem(
                modifier = Modifier.padding(8.dp),
                wish = it,
                ownerId = ownerId,
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
fun WishlistMemberScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        val previewMember: FamilyMember = koinInject(named(PreviewData.MustermannChristianMember))
        WishareTheme {
            WishlistMemberContent(
                member = koinInject(named(PreviewData.MustermannSimonMember)),
                ownerId = koinInject(named(PreviewData.MustermannChristianId)),
                getMemberById = { previewMember }
            )
        }
    }
}