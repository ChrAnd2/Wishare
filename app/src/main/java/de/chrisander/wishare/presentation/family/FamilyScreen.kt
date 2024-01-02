package de.chrisander.wishare.presentation.family

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.UiImage
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.presentation.family.components.FamilyMemberListItem
import de.chrisander.wishare.presentation.navigation.HomeNavGraph
import de.chrisander.wishare.ui.theme.WishareTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@HomeNavGraph
@Destination(
    navArgsDelegate = FamilyScreenNavArgs::class
)
@Composable
fun FamilyScreen(
    viewModel: FamilyViewModel = koinViewModel(),
    navigator: DestinationsNavigator
){
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                FamilyViewModelEvent.NavigateUp -> navigator.popBackStack()
            }
        }
    }

    FamilyContent(
        family = state.family,
        members = state.members,
        onImageEdited = { viewModel.onEvent(FamilyUiEvent.OnFamilyImageChanged(it)) },
        onFamilyNameChanged = { viewModel.onEvent(FamilyUiEvent.OnFamilyNameChanged(it)) },
        onRemoveClicked = { viewModel.onEvent(FamilyUiEvent.OnRemoveClicked) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FamilyContent(
    family: Family,
    members: List<FamilyMember>,
    onImageEdited: (UiImage) -> Unit = {},
    onFamilyNameChanged: (String) -> Unit = {},
    onRemoveClicked: () -> Unit = {}
){
    val context = LocalContext.current
    var familyNameInput by remember { mutableStateOf(family.name) }
    var familyImage by remember { mutableStateOf(family.image) }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if(uri == null)
                return@rememberLauncherForActivityResult

            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
            val uiImage = UiImage.Dynamic(bitmap)
            onImageEdited(uiImage)
            familyImage = uiImage
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = familyImage.getBitmap(context).asImageBitmap(),
                contentScale = ContentScale.Crop,
                contentDescription = family.name
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = stringResource(id = R.string.edit),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 150f
                    )
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                TextField(
                    modifier = Modifier.background(
                        Color.Transparent
                    ),
                    value = familyNameInput,
                    onValueChange = { newValue ->
                        familyNameInput = newValue
                        onFamilyNameChanged(newValue)
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.family_name_placeholder))
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    )
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = stringResource(id = R.string.family_members_title),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement  = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            repeat(members.size) {
                FamilyMemberListItem(member = members[it])
            }
            Image(
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(id = R.string.add_family_member),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
        if(false){
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.leave_family),
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            onClick = { onRemoveClicked() }
        ) {
            Text(
                text = stringResource(id = R.string.delete_family),
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
fun FamilyScreenPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            FamilyContent(
                family = koinInject(named(PreviewData.MustermannFamily)),
                members = koinInject(named(PreviewData.MustermannMembers))
            )
        }
    }
}