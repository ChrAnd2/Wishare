package de.chrisander.wishare.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.ui.theme.WishareTheme
import de.chrisander.wishare.ui.theme.wishByOther
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@Composable
fun WishListItem(
    modifier: Modifier = Modifier,
    wish: Wish,
    ownerId: FamilyMemberId,
    getMemberById: (FamilyMemberId) -> FamilyMember,
    onReserveClicked: (Wish) -> Unit = {},
    onBoughtClicked: (Wish) -> Unit = {},
    onCancelReservationClicked: (Wish) -> Unit = {},
) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = stringResource(id = wish.state.nameRes),
                    color = wish.state.color,
                    textAlign = TextAlign.Center
                )
                Column(modifier = Modifier.weight(0.8f)) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        text = wish.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Divider(modifier = Modifier.padding(4.dp), thickness = 1.dp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(id = R.drawable.ic_description),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
                        )
                        Text(
                            text = wish.description,
                            style = TextStyle(
                                fontSize = 14.sp
                            ),
                        )
                    }
                    Divider(modifier = Modifier.padding(4.dp), thickness = 1.dp)
                    if(wish.url != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier.padding(end = 8.dp),
                                painter = painterResource(id = R.drawable.ic_link),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
                            )
                            ClickableText(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                        append(wish.url.toString())
                                    }
                                },
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                onClick = { uriHandler.openUri(wish.url.toString()) }
                            )
                        }
                        Divider(modifier = Modifier.padding(4.dp), thickness = 1.dp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val sdf = SimpleDateFormat("dd.MM.yyyy")
                        val date = Date(wish.creationDate)
                        Image(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(id = R.drawable.ic_date),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
                        )
                        Text(
                            text = sdf.format(date),
                            style = TextStyle(
                                fontSize = 14.sp
                            ),
                        )
                    }

                }
            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                val allowedToEdit =
                    (wish.state as? WishState.HasByMember)?.byMemberId == ownerId || wish.state !is WishState.HasByMember
                if (allowedToEdit && wish.state !is WishState.Open && wish.state !is WishState.HandedOver) {
                    Button(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.error)
                            .weight(0.5f),
                        onClick = { onCancelReservationClicked(wish) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(text = stringResource(id = R.string.wish_cancel_reservation))
                    }
                }
                if (allowedToEdit && wish.state !is WishState.HandedOver) {
                    Button(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .weight(0.5f),
                        onClick = {
                            when(wish.state){
                                is WishState.Bought -> onBoughtClicked(wish)
                                is WishState.Reserved -> onReserveClicked(wish)
                                else -> {}
                            }
                        },
                    ) {
                        when (wish.state) {
                            is WishState.Open -> Text(text = stringResource(id = R.string.reserve_wish))
                            is WishState.Reserved -> Text(text = stringResource(id = R.string.wish_bought))
                            is WishState.Bought -> Text(text = stringResource(id = R.string.wish_handed_over))
                            else -> {}
                        }
                    }
                }
                if (!allowedToEdit && wish.state !is WishState.HandedOver && wish.state !is WishState.Open) {
                    Button(
                        modifier = Modifier
                            .background(wishByOther)
                            .weight(0.5f),
                        onClick = {},
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = wishByOther)
                    ) {
                        when (wish.state) {
                            is WishState.Reserved -> {
                                Text(
                                    text = stringResource(
                                        id = R.string.wish_reserved_by,
                                        getMemberById(wish.state.byMemberId)
                                    )
                                )
                            }
                            is WishState.Bought -> {
                                Text(
                                    text = stringResource(
                                        id = R.string.wish_bought_by,
                                        getMemberById(wish.state.byMemberId)
                                    )
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WishListItemPreview() {
    val previewMember: FamilyMember = koinInject(named(PreviewData.MustermannChristianMember))
    WishareTheme {
        WishListItem(
            wish = koinInject(named(PreviewData.MustermannChristianWish)),
            ownerId = koinInject(named(PreviewData.MustermannSophieId)),
            getMemberById = { previewMember }
        )
    }
}