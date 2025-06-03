package com.silentcid.homemind.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class CarouselItem(
    val itemId: Int,
    val imageVector: ImageVector,
    @DrawableRes val backgroundImageResId: Int,
    @StringRes val contentDescriptionResId: Int,
    )
