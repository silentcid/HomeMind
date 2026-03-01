package com.silentcid.homemind.ui.screen.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.R
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.ui.components.ExpandableGroceryList
import com.silentcid.homemind.ui.theme.HomeMindTheme
import com.silentcid.homemind.ui.theme.PrimaryLight

data class HomeFeature(
    val id: Int,
    val titleResId: Int,
    val icon: ImageVector,
    val gradientColors: List<Color>,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    groceryItems: List<GroceryItem>,
    onNavigateToGrocery: () -> Unit,
    onNavigateToSuggestions: () -> Unit,
    onToggleLanguage: () -> Unit,
    onNavigateToGroceryItemDetails: (Int) -> Unit,
    onGroceryItemCheckBoxClicked: (groceryItem: GroceryItem) -> Unit,
) {
    val features = listOf(
        HomeFeature(
            id = 0,
            titleResId = R.string.grocery_button,
            icon = Icons.Outlined.ShoppingCart,
            gradientColors = listOf(Color(0xFF00897B), Color(0xFF26A69A)),
            onClick = onNavigateToGrocery
        ),
        HomeFeature(
            id = 1,
            titleResId = R.string.suggestion_button,
            icon = Icons.Default.Lightbulb,
            gradientColors = listOf(Color(0xFFFF8F00), Color(0xFFFFB74D)),
            onClick = onNavigateToSuggestions
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    FilledTonalIconButton(onClick = onToggleLanguage) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.change_language_button)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Welcome Section
                item {
                    WelcomeSection()
                }

                // Feature Cards
                item {
                    Text(
                        text = if (groceryItems.isEmpty()) {
                            stringResource(R.string.welcome_text)
                        } else {
                            stringResource(R.string.grocery_count, groceryItems.size)
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        features.forEach { feature ->
                            FeatureCard(
                                feature = feature,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Grocery List Section
                item {
                    AnimatedVisibility(
                        visible = groceryItems.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.grocery_list_header),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                            )
                            
                            GroceryListCard(
                                groceryItems = groceryItems,
                                onCheckChange = { groceryItem ->
                                    Log.d("HomeScreen", "onCheckChange called for '${groceryItem.name}'.")
                                    onGroceryItemCheckBoxClicked(groceryItem)
                                },
                                onItemClick = { groceryItem ->
                                    onNavigateToGroceryItemDetails(groceryItem.id)
                                }
                            )
                        }
                    }
                }
                
                // Empty State
                item {
                    AnimatedVisibility(
                        visible = groceryItems.isEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        EmptyGroceryState(
                            onAddClick = onNavigateToGrocery
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun WelcomeSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.welcome_text),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (java.util.Locale.getDefault().language == "ja") {
                        "AI搭載スマートホームアシスタント"
                    } else {
                        "Your AI-powered smart home assistant"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(
    feature: HomeFeature,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = feature.onClick,
        modifier = modifier
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = feature.gradientColors
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                
                Text(
                    text = stringResource(feature.titleResId),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun GroceryListCard(
    groceryItems: List<GroceryItem>,
    onCheckChange: (GroceryItem) -> Unit,
    onItemClick: (GroceryItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groceryItems.take(5).forEach { item ->
                GroceryItemRow(
                    item = item,
                    onCheckChange = { onCheckChange(item) },
                    onClick = { onItemClick(item) }
                )
            }
            
            if (groceryItems.size > 5) {
                Text(
                    text = "+${groceryItems.size - 5} more items",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun GroceryItemRow(
    item: GroceryItem,
    onCheckChange: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { onCheckChange() }
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = if (item.isChecked) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
            
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = "x${item.quantity}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun EmptyGroceryState(
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (java.util.Locale.getDefault().language == "ja") {
                    "買い物リストが空です"
                } else {
                    "Your grocery list is empty"
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onAddClick,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.grocery_button))
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun HomeScreenPreview() {
    HomeMindTheme(darkTheme = true) {
        HomeScreen(
            groceryItems = listOf(
                GroceryItem(name = "Apples", quantity = 2),
                GroceryItem(name = "Milk", quantity = 1)
            ),
            onNavigateToGrocery = {},
            onNavigateToSuggestions = {},
            onToggleLanguage = {},
            onGroceryItemCheckBoxClicked = {},
            onNavigateToGroceryItemDetails = {},
        )
    }
}
