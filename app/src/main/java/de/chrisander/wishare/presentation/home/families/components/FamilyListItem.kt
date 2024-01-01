package de.chrisander.wishare.presentation.home.families.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import de.chrisander.wishare.R
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.presentation.di.PreviewData
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun FamilyListItem(
    family: Family,
    onClicked: (Family) -> Unit = {},
    onEditClicked: (Family) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .clickable {
                onClicked(family)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = family.image.asImageBitmap(),
                contentDescription = family.name
            )
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
                    .fillMaxSize()
                    .padding(12.dp)
                    .clickable {
                        onEditClicked(family)
                    },
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier
                        .size(15.dp, 15.dp),
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = stringResource(id = R.string.edit)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = family.name,
                    style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FamilyListItemPreview() {
    FamilyListItem(
        family = koinInject(named(PreviewData.MustermannFamily))
    )
}