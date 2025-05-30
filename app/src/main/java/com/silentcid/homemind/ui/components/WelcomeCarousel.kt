package com.silentcid.homemind.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.R
import com.silentcid.homemind.data.models.CarouselItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeCarousel(
    onItemClick: (CarouselItem) -> Unit,
) {
    val addToListId = 0
    val suggestionsId = 1
    val takePictureId = 2

    val carouselItems = remember {
        listOf(
            CarouselItem(addToListId, R.drawable.groceries, contentDescriptionResId = R.string.grocery_button),
            CarouselItem(suggestionsId, R.drawable.suggestions, contentDescriptionResId = R.string.suggestion_button),
            CarouselItem(takePictureId, R.drawable.camera, contentDescriptionResId = R.string.camera_button)
        )
    }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { carouselItems.count() },
        itemWidth = 186.dp,
        itemSpacing = 16.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { itemCount ->
        val item = carouselItems[itemCount]

        Box(
            modifier = Modifier
                .width(186.dp)
                .height(285.dp)
                .clip(MaterialTheme.shapes.extraLarge) // Clipping the whole item
                .clickable { onItemClick(item) }
        ) {
            Image(
                painter = painterResource(id = item.drawableResId),
                contentDescription = stringResource(item.contentDescriptionResId),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = stringResource(id = item.contentDescriptionResId),
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun PreviewWelcomeCarousel() {
    WelcomeCarousel { }
}
