package de.chrisander.wishare.presentation.wishlist_members.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisander.wishare.R
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import timber.log.Timber

@Composable
fun WishlistMemberListItem(
    modifier: Modifier = Modifier,
    member: FamilyMember,
    ownerId: FamilyMemberId,
    onMemberClicked: (FamilyMember) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onMemberClicked(member)
            }
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Max)) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.33f),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.demo_profile_picture),
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .weight(0.66f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = member.name,
                )
                Divider(modifier = Modifier.padding(vertical = 6.dp), thickness = 1.dp)
                val numOpenWishes = member.wishes.map { it.state }.filterIsInstance<WishState.Open>().size
                val numAssignedWishes = member.wishes.filter {
                    (it.state is WishState.Reserved && it.state.byMemberId == ownerId) ||
                            (it.state is WishState.Bought && it.state.byMemberId == ownerId)
                }.size
                Text(
                    text = pluralStringResource(
                        R.plurals.open_wishes,
                        numOpenWishes,
                        numOpenWishes
                    ),
                )
                Divider(modifier = Modifier.padding(vertical = 6.dp), thickness = 1.dp)
                Text(
                    text = pluralStringResource(
                        R.plurals.assigned_wishes,
                        numAssignedWishes,
                        numAssignedWishes
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistMemberListItemPreview() {
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            WishlistMemberListItem(
                member = koinInject(named(PreviewData.MustermannSimonMember)),
                ownerId = koinInject(named(PreviewData.MustermannChristianId))
            )
        }
    }
}