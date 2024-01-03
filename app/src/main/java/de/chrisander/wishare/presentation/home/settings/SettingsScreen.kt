package de.chrisander.wishare.presentation.home.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.chrisander.wishare.R
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.presentation.NavGraphs
import de.chrisander.wishare.presentation.destinations.SignInScreenDestination
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    navigator: DestinationsNavigator
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SettingsViewModelEvent.NavigateToSignIn -> {
                    navigator.popBackStack(NavGraphs.home.route, inclusive = true)
                }
            }
        }
    }
    
    SettingsScreenContent(
        modifier = modifier,
        state = state,
        onSignOutClicked = { viewModel.onEvent(SettingsUiEvent.OnSignOutClicked) }
    )
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    state: SettingsScreenState,
    onSignOutClicked: () -> Unit = {}
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(state.userData?.profilePictureUrl != null){
            AsyncImage(
                model = state.userData.profilePictureUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(state.userData?.username != null){
            Text(
                text = state.userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Button(onClick = onSignOutClicked) {
            Text(text = stringResource(id = R.string.sign_out))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            SettingsScreenContent(state = SettingsScreenState(null))
        }
    }
}