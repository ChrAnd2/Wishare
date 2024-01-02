package de.chrisander.wishare.presentation.family.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisander.wishare.R
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun FamilyMemberListItem(
    member: FamilyMember,
    onRemoveClicked: (FamilyMember) -> Unit = {}
){
    Box(modifier = Modifier.wrapContentWidth(), contentAlignment = Alignment.Center) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .background(Color.Transparent)
                .wrapContentWidth()

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = member.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(start = 10.dp),
                    maxLines = 1
                )
                Image(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(18.dp)
                        .clickable { onRemoveClicked(member) },
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = stringResource(id = R.string.remove_family_member),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                    alignment = Alignment.CenterEnd
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FamilyMemberListItemPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            FamilyMemberListItem(member = koinInject(named(PreviewData.MustermannSophieMember)))
        }
    }
}
