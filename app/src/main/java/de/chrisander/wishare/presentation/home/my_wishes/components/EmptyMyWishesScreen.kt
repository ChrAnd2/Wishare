package de.chrisander.wishare.presentation.home.my_wishes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.chrisander.wishare.R
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.KoinApplication

@Composable
fun EmptyMyWishesScreen(
    modifier: Modifier = Modifier,
    onCreateWishClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.empty_my_wishes_list),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier.padding(end = 16.dp),
                onClick = onCreateWishClicked,
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.create_wish)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EmptyMyWishesScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            EmptyMyWishesScreen()
        }
    }
}