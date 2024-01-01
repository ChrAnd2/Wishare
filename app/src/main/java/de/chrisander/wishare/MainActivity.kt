package de.chrisander.wishare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.presentation.di.previewModule
import de.chrisander.wishare.presentation.di.uiModule
import de.chrisander.wishare.presentation.home.HomeScreen
import de.chrisander.wishare.presentation.home.my_wishes.MyWishesScreen
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishareTheme {
                HomeScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            HomeScreen()
        }
    }
}