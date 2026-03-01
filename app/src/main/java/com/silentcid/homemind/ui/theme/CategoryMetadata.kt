package com.silentcid.homemind.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryInfo(
    val icon: ImageVector,
    val color: Color,
    val containerColor: Color,
    val onContainerColor: Color,
    val gradient: List<Color> = listOf(color, color.copy(alpha = 0.8f))
)

val CategoryMetadataMap = mapOf(
    "dairy" to CategoryInfo(
        icon = Icons.Default.LocalDrink,
        color = Color(0xFF2196F3),
        containerColor = Color(0xFFD1E9FF),
        onContainerColor = Color(0xFF001D33),
        gradient = listOf(Color(0xFF2196F3), Color(0xFF64B5F6))
    ),
    "bakery" to CategoryInfo(
        icon = Icons.Default.BakeryDining,
        color = Color(0xFF795548),
        containerColor = Color(0xFFF3EBE9),
        onContainerColor = Color(0xFF2D1600),
        gradient = listOf(Color(0xFF795548), Color(0xFFA1887F))
    ),
    "fruits" to CategoryInfo(
        icon = Icons.Default.Eco,
        color = Color(0xFFF44336),
        containerColor = Color(0xFFFFDAD6),
        onContainerColor = Color(0xFF410002),
        gradient = listOf(Color(0xFFF44336), Color(0xFFEF5350))
    ),
    "vegetables" to CategoryInfo(
        icon = Icons.Default.Grass,
        color = Color(0xFF4CAF50),
        containerColor = Color(0xFFCEEECC),
        onContainerColor = Color(0xFF002106),
        gradient = listOf(Color(0xFF4CAF50), Color(0xFF81C784))
    ),
    "meat" to CategoryInfo(
        icon = Icons.Default.Restaurant,
        color = Color(0xFFE91E63),
        containerColor = Color(0xFFFFD9E2),
        onContainerColor = Color(0xFF3E001D),
        gradient = listOf(Color(0xFFE91E63), Color(0xFFF06292))
    ),
    "beverages" to CategoryInfo(
        icon = Icons.Default.Coffee,
        color = Color(0xFF00BCD4),
        containerColor = Color(0xFFA2EFFF),
        onContainerColor = Color(0xFF001F25),
        gradient = listOf(Color(0xFF00BCD4), Color(0xFF4DD0E1))
    ),
    "snacks" to CategoryInfo(
        icon = Icons.Default.Cookie,
        color = Color(0xFFFF9800),
        containerColor = Color(0xFFFFDDB3),
        onContainerColor = Color(0xFF291800),
        gradient = listOf(Color(0xFFFF9800), Color(0xFFFFB74D))
    ),
    "toiletries" to CategoryInfo(
        icon = Icons.Default.CleanHands,
        color = Color(0xFF9C27B0),
        containerColor = Color(0xFFF7D8FF),
        onContainerColor = Color(0xFF220033),
        gradient = listOf(Color(0xFF9C27B0), Color(0xFFBA68C8))
    ),
    "sauces" to CategoryInfo(
        icon = Icons.Default.Liquor,
        color = Color(0xFFFF5722),
        containerColor = Color(0xFFFFDAD1),
        onContainerColor = Color(0xFF3E0400),
        gradient = listOf(Color(0xFFFF5722), Color(0xFFFF8A65))
    ),
    "household" to CategoryInfo(
        icon = Icons.Default.Home,
        color = Color(0xFF00897B),
        containerColor = Color(0xFFB2DFDB),
        onContainerColor = Color(0xFF00251E),
        gradient = listOf(Color(0xFF00897B), Color(0xFF26A69A))
    ),
    "meals" to CategoryInfo(
        icon = Icons.Default.Restaurant,
        color = Color(0xFFFF8F00),
        containerColor = Color(0xFFFFE0B2),
        onContainerColor = Color(0xFF2D1600),
        gradient = listOf(Color(0xFFFF8F00), Color(0xFFFFB74D))
    ),
    "cleaning" to CategoryInfo(
        icon = Icons.Default.CleaningServices,
        color = Color(0xFF1976D2),
        containerColor = Color(0xFFBBDEFB),
        onContainerColor = Color(0xFF0D47A1),
        gradient = listOf(Color(0xFF1976D2), Color(0xFF42A5F5))
    ),
    "shopping" to CategoryInfo(
        icon = Icons.Default.ShoppingBag,
        color = Color(0xFF607D8B),
        containerColor = Color(0xFFCFD8DC),
        onContainerColor = Color(0xFF263238),
        gradient = listOf(Color(0xFF607D8B), Color(0xFF90A4AE))
    ),
    "other" to CategoryInfo(
        icon = Icons.Default.Inventory2,
        color = Color(0xFF607D8B),
        containerColor = Color(0xFFE2E2E6),
        onContainerColor = Color(0xFF1A1C1E),
        gradient = listOf(Color(0xFF607D8B), Color(0xFF90A4AE))
    )
)

val LocalCategoryMetadata = staticCompositionLocalOf { CategoryMetadataMap }

val MaterialTheme.categoryMetadata: Map<String, CategoryInfo>
    @Composable
    @ReadOnlyComposable
    get() = LocalCategoryMetadata.current
