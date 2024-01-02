package de.chrisander.wishare.presentation.home.families.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisander.wishare.di.appPreviewModules
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.presentation.di.PreviewData
import de.chrisander.wishare.ui.theme.WishareTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun FamilyList(
    modifier: Modifier = Modifier,
    families: List<Family>,
    onFamilyClicked: (Family) -> Unit = {},
    onEditFamilyClicked: (Family) -> Unit = {},
    onAddFamilyClicked: () -> Unit = {}
){
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(families){ family ->
            FamilyListItem(family, onFamilyClicked, onEditFamilyClicked)
        }
        item {
            AddFamilyListItem(
                onAddFamilyClicked = onAddFamilyClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FamilyListPreview(){
    KoinApplication(application = {
        modules(appPreviewModules)
    }) {
        WishareTheme {
            FamilyList(
                families = koinInject(named(PreviewData.Families))
            )
        }
    }
}