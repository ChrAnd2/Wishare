package de.chrisander.wishare.presentation.my_wish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import de.chrisander.wishare.R
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.family.FamilyViewModelEvent
import de.chrisander.wishare.presentation.navigation.HomeNavGraph
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import java.net.MalformedURLException
import java.net.URL

@HomeNavGraph
@Destination(
    navArgsDelegate = MyWishNavArgs::class
)
@Composable
fun MyWishScreen(
    viewModel: MyWishViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                MyWishViewModelEvent.NavigateUp -> navigator.popBackStack()
            }
        }
    }

    MyWishScreenContent(
        state = state,
        onWishTitleChanged = { viewModel.onEvent(MyWishUiEvent.OnWishTitleChanged(it)) },
        onWishDescriptionChanged = { viewModel.onEvent(MyWishUiEvent.OnWishDescriptionChanged(it)) },
        onWishUrlChanged = { viewModel.onEvent(MyWishUiEvent.OnWishUrlChanged(it)) },
        onWishRemoveClicked = { viewModel.onEvent(MyWishUiEvent.OnWishRemoveClicked) }
    )
}

@Composable
fun MyWishScreenContent(
    state: MyWishScreenState,
    onWishTitleChanged: (String) -> Unit = {},
    onWishDescriptionChanged: (String) -> Unit = {},
    onWishUrlChanged: (String) -> Unit = {},
    onWishRemoveClicked: () -> Unit = {}
){
    var wishTitleInput by remember { mutableStateOf(state.wish.name) }
    var wishDescriptionInput by remember { mutableStateOf(state.wish.description) }
    var wishUrlInput by remember { mutableStateOf(state.wish.url?.toString() ?: "") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    fun validateUrl(text: String): Boolean {
        val error = try {
            URL(text)
            false
        } catch (e: MalformedURLException){
            true
        }
        isError = error
        return !error
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .size(150.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_present),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = wishTitleInput,
            onValueChange = {
                wishTitleInput = it
                onWishTitleChanged(it)
            },
            placeholder = {
                Text(text = stringResource(id = R.string.wish_name_placeholder))
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            )
        )
        Divider(
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = wishDescriptionInput,
            onValueChange = {
                wishDescriptionInput = it
                onWishDescriptionChanged(it)
            },
            placeholder = {
                Text(text = stringResource(id = R.string.wish_description_placeholder))
            },
            minLines = 4,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = wishUrlInput,
            onValueChange = {
                wishUrlInput = it
                if(validateUrl(it))
                    onWishUrlChanged(it)
            },
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
            ),
            label = {
                Text(text = stringResource(id = R.string.wish_url_placeholder))
            },
            isError = isError,
            supportingText = {
                if(isError)
                    Text(text = stringResource(id = R.string.error_invalid_url))
            },
            colors = TextFieldDefaults.colors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            onClick = { onWishRemoveClicked() }
        ) {
            Text(
                text = stringResource(id = R.string.delete_wish),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyWishScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            MyWishScreenContent(
                state = MyWishScreenState(wish = koinInject(named(PreviewData.MustermannChristianWish)))
            )
        }
    }
}