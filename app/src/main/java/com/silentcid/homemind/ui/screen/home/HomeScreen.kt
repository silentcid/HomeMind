package com.silentcid.homemind.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.R
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.ui.components.GroceryItemCard
import com.silentcid.homemind.ui.components.ToolBar
import com.silentcid.homemind.ui.components.WelcomeCarousel
import com.silentcid.homemind.ui.theme.HomeMindTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    groceryItems: List<GroceryItem>,
    onNavigateToGrocery: () -> Unit,
    onNavigateToSuggestions: () -> Unit
) {
    Scaffold(
        topBar = {
            ToolBar(
                R.string.app_name,
                onNavigationClick = {}
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = dimensionResource(R.dimen.screen_margins)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_margins)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = stringResource(R.string.welcome_text),
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier
                            .padding(top = 16.dp, start = 8.dp, bottom = 16.dp)
                            
                    )
                }

                item {
                    WelcomeCarousel { /* Handle carousel click if needed */ }
                }

                item {
                    Text(
                        text = stringResource(R.string.grocery_count, groceryItems.size),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                items(groceryItems) { item ->
                    GroceryItemCard(item)
                }

            }
        }
    )
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
            onNavigateToSuggestions = {}
        )
    }
}
