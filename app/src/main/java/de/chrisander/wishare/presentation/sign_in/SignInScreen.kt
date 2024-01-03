package de.chrisander.wishare.presentation.sign_in

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.chrisander.wishare.R
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.presentation.destinations.FamilyScreenDestination
import de.chrisander.wishare.presentation.destinations.HomeScreenDestination
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.home.families.FamiliesContent
import de.chrisander.wishare.presentation.home.families.FamiliesScreenState
import de.chrisander.wishare.presentation.home.families.FamiliesViewModelEvent
import de.chrisander.wishare.presentation.navigation.HomeNavGraph
import de.chrisander.wishare.presentation.navigation.SignInNavGraph
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@SignInNavGraph(start = true)
@Destination
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val googleAuthUiClient = remember {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SignInViewModelEvent.NavigateToHome -> {
                    navigator.navigate(HomeScreenDestination)
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == ComponentActivity.RESULT_OK){
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onEvent(SignInUiEvent.OnSignInResult(signInResult))
                }
            }
        }
    )

    SignInScreenContent(
        state = state,
        onSignInClicked = { scope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
            )
        } }
    )
}

@Composable
fun SignInScreenContent(
    state: SignInState,
    onSignInClicked: () -> Unit = {}
){
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError){
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Button(onClick = onSignInClicked) {
            Text(text = stringResource(id = R.string.sign_in))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            SignInScreenContent(
                state = SignInState()
            )
        }
    }
}