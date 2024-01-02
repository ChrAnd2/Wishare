package de.chrisander.wishare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import com.ramcosta.composedestinations.DestinationsNavHost
import de.chrisander.wishare.presentation.NavGraphs
import de.chrisander.wishare.ui.theme.WishareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishareTheme {
                window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
                window.navigationBarColor = MaterialTheme.colorScheme.background.toArgb()
                DestinationsNavHost(navGraph = NavGraphs.home)
            }
        }
    }
}