package com.silentcid.homemind.data.models

import androidx.annotation.DrawableRes

data class CarouselItem(
    val itemId: Int,
    @DrawableRes val drawableResId: Int,
    val contentDescriptionResId: Int,
    )
