package de.chrisander.wishare.presentation.home.my_wishes.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.chrisander.wishare.R
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MyWishListItem(
    modifier: Modifier = Modifier,
    myWish: Wish,
    onEditWishClicked: (Wish) -> Unit = {}
){
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = myWish.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center
            )
            Divider(modifier = Modifier.padding(4.dp), thickness = 1.dp)
            WishInfoItem(text = myWish.description, iconRes = R.drawable.ic_description)
            Divider(modifier = Modifier.padding(4.dp), thickness = 1.dp)
            if(myWish.url != null) {
                ClickableWishInfoItem(
                    text = myWish.url.toString(),
                    iconRes = R.drawable.ic_link,
                    onClick = { uriHandler.openUri(myWish.url.toString()) }
                )
                Divider(modifier = Modifier.padding(4.dp), thickness = 1.dp)
            }

            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val date = Date(myWish.creationDate)
            WishInfoItem(
                modifier = Modifier.padding(bottom = 8.dp),
                text = sdf.format(date),
                iconRes = R.drawable.ic_date
            )
            Button(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth(),
                onClick = {
                    onEditWishClicked(myWish)
                },
            ) {
                Text(text = stringResource(id = R.string.edit_wish))
            }
        }
    }
}

@Composable
fun WishInfoItem(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int,
){
    Row(
        modifier = modifier.padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(end = 8.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp
            ),
        )
    }
}

@Composable
fun ClickableWishInfoItem(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(end = 8.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
        )
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text)
                }
            },
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            onClick = { onClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyWishListItemPreview(){
    WishareTheme {
        MyWishListItem(myWish = koinInject(named(PreviewData.MustermannChristianWish)))
    }
}