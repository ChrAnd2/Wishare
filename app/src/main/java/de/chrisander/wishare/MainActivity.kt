package de.chrisander.wishare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.ramcosta.composedestinations.DestinationsNavHost
import de.chrisander.wishare.presentation.NavGraphs
import de.chrisander.wishare.presentation.sign_in.GoogleAuthUiClient
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishareTheme {
                window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
                window.navigationBarColor = MaterialTheme.colorScheme.background.toArgb()
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}