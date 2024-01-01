package de.chrisander.wishare.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomMenu(
    modifier: Modifier = Modifier,
    items: List<BottomMenuContent>,
    initialSelectedItemIndex: Int = 0,
    onItemClick: (BottomMenuContent) -> Unit
) {
    var selectedItemIndex by remember {
        mutableIntStateOf(initialSelectedItemIndex)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
    ) {
        items.forEachIndexed { index, bottomSheetContent ->
            BottomMenuItem(
                item = bottomSheetContent,
                isSelected = index == selectedItemIndex
            ) {
                selectedItemIndex = index
                onItemClick(bottomSheetContent)
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    inactiveTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onItemClick() }
    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = stringResource(item.titleRes),
            modifier = Modifier.size(40.dp),
            tint = if (isSelected) activeTextColor else inactiveTextColor
        )
        Text(
            text = stringResource(item.titleRes),
            color = if(isSelected) activeTextColor else inactiveTextColor
        )
    }
}