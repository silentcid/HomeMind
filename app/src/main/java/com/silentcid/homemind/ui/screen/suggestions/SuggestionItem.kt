package com.silentcid.homemind.ui.screen.suggestions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SuggestionItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color,
    val tags: List<String> = emptyList(),
    val priority: String? = null,
    val ingredients: List<String> = emptyList(),
    val instructions: String = ""
)
