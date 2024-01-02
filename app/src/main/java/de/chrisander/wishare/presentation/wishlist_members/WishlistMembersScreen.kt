package de.chrisander.wishare.presentation.wishlist_members

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.destinations.WishlistMemberScreenDestination
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.di.previewModule
import de.chrisander.wishare.presentation.di.uiModule
import de.chrisander.wishare.presentation.navigation.HomeNavGraph
import de.chrisander.wishare.presentation.wishlist_members.components.WishlistMemberListItem
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@HomeNavGraph
@Destination(
    navArgsDelegate = WishlistMembersNavArgs::class
)
@Composable
fun WishlistMembersScreen(
    viewModel: WishlistMembersViewModel = koinViewModel(),
    navigator: DestinationsNavigator
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is WishlistMembersViewModelEvent.NavigateToMemberWishlist -> {
                    navigator.navigate(WishlistMemberScreenDestination(event.memberId))
                }
            }
        }
    }

    WishlistMembersContent(
        family = state.family,
        members = state.members,
        ownerId = state.ownerId,
        onMemberClicked = { viewModel.onEvent(WishlistMembersUiEvent.OnMemberClicked(it)) }
    )
}

@Composable
fun WishlistMembersContent(
    family: Family,
    members: List<FamilyMember>,
    ownerId: FamilyMemberId,
    onMemberClicked: (FamilyMember) -> Unit = {}
){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(members) {
            WishlistMemberListItem(
                modifier = Modifier.padding(8.dp),
                member = it,
                ownerId = ownerId,
                onMemberClicked = onMemberClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistMembersScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            WishlistMembersContent(
                family = koinInject(named(PreviewData.MustermannFamily)),
                members = koinInject(named(PreviewData.MustermannMembers)),
                ownerId = koinInject(named(PreviewData.MustermannChristianId))
            )
        }
    }
}