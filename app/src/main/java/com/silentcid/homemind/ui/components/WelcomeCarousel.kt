/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */


package com.silentcid.homemind.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.LightbulbCircle
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.material3.carousel.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.R
import com.silentcid.homemind.data.models.CarouselItem

object WelcomeCarouselDefaults {
    const val ADD_TO_LIST_ID = 0
    const val SUGGESTIONS_ID = 1
    const val TAKE_A_PICTURE_ID = 2

    val itemWidth = 160.dp
    val itemHeight = 220.dp
    val itemSpacing = 12.dp
    val carouselHorizontalPadding = 16.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeCarousel(
    onItemClick: (CarouselItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val carouselItems = remember {
        listOf(
            CarouselItem(
                itemId = WelcomeCarouselDefaults.ADD_TO_LIST_ID,
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescriptionResId = R.string.grocery_list_header,
                backgroundImageResId = R.drawable.groceries
            ),
            CarouselItem(
                itemId = WelcomeCarouselDefaults.SUGGESTIONS_ID,
                imageVector = Icons.Outlined.LightbulbCircle,
                contentDescriptionResId = R.string.suggestion_button,
                backgroundImageResId = R.drawable.suggestions
            ),
            CarouselItem(
                itemId = WelcomeCarouselDefaults.TAKE_A_PICTURE_ID,
                imageVector = Icons.Outlined.PhotoCamera,
                contentDescriptionResId = R.string.camera_button,
                backgroundImageResId = R.drawable.camera
            )
        )
    }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { carouselItems.count() },
        modifier = modifier,
        itemWidth = WelcomeCarouselDefaults.itemWidth,
        itemSpacing = WelcomeCarouselDefaults.itemSpacing,
        contentPadding = PaddingValues(horizontal = WelcomeCarouselDefaults.carouselHorizontalPadding)
    ) { itemIndex ->
        val item = carouselItems[itemIndex]
        key(item.itemId) {
            ElevatedCard(
                onClick = { onItemClick(item) },
                modifier = Modifier
                    .width(WelcomeCarouselDefaults.itemWidth)
                    .height(WelcomeCarouselDefaults.itemHeight),
                shape = MaterialTheme.shapes.large, // Keep rounded corners
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                ),
                // We'll control background color via the Box and Image
                colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent)

            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Background Image
                    item.backgroundImageResId?.let { imageRes ->
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null, // Decorative
                            contentScale = ContentScale.Crop, // Crop to fill bounds
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Scrim for text readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.2f), // Darker at top
                                        Color.Black.copy(alpha = 0.6f)  // Darker at bottom for text
                                    )
                                )
                            )
                    )

                    // Content (Icon and Text)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = item.imageVector,
                            contentDescription = null, // Text below provides the label
                            modifier = Modifier.size(64.dp), // Slightly smaller icon if bg image is busy
                            tint = Color.White // Ensure icon is visible on dark scrim
                        )
                        Text(
                            text = stringResource(id = item.contentDescriptionResId),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            color = Color.White // Ensure text is visible on dark scrim
                        )
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode Welcome Carousel Visual"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Welcome Carousel Visual"
)
@Composable
fun PreviewWelcomeCarousel() {
    MaterialTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            WelcomeCarousel(
                onItemClick = { /* Handle click in preview if needed */ }
            )
        }
    }
}