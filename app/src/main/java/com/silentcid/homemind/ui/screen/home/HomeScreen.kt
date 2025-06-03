    package com.silentcid.homemind.ui.screen.home

    import android.content.res.Configuration
    import android.util.Log
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.material3.*
    import androidx.compose.material3.carousel.CarouselDefaults
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
    import com.silentcid.homemind.ui.components.ExpandableGroceryList
    import com.silentcid.homemind.ui.components.ToolBar
    import com.silentcid.homemind.ui.components.WelcomeCarousel
    import com.silentcid.homemind.ui.components.WelcomeCarouselDefaults
    import com.silentcid.homemind.ui.theme.HomeMindTheme

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(
        groceryItems: List<GroceryItem>,
        onNavigateToGrocery: () -> Unit,
        onNavigateToSuggestions: () -> Unit,
        onToggleLanguage: () -> Unit,
        onNavigateToGroceryItemDetails: () -> Unit,
        onGroceryItemCheckBoxClicked: (groceryItem: GroceryItem) -> Unit,
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.welcome_text),
                                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Medium)
                            )

                            Spacer(modifier = Modifier.weight(1f)) // pushes the button to the right

                            Button(
                                onClick = onToggleLanguage,
                                modifier = Modifier
                                    .width(140.dp) // fixed width
                                    .height(60.dp) // fixed height
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("🌐", modifier = Modifier.padding(end = 8.dp)) // icon

                                    Column {
                                        Text(
                                            text = stringResource(R.string.change_language_button),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        // To be implemented
                        WelcomeCarousel(
                            onItemClick = { carouselItem ->
                                when (carouselItem.itemId) {
                                    WelcomeCarouselDefaults.ADD_TO_LIST_ID -> onNavigateToGrocery()
                                    WelcomeCarouselDefaults.SUGGESTIONS_ID -> onNavigateToSuggestions()
                                    WelcomeCarouselDefaults.TAKE_A_PICTURE_ID -> onNavigateToGroceryItemDetails()
                                }
                            }
                        )
                    }

                    item {
                        Text(
                            text = stringResource(R.string.grocery_count, groceryItems.size),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    item {

                        ExpandableGroceryList(
                            title = R.string.grocery_list_header,
                            groceryList = groceryItems,
                            onCheckChange = {
                                groceryItem ->
                                Log.d("HomeScreen", "onCheckChange called for '${groceryItem.name}'.")
                                onGroceryItemCheckBoxClicked(groceryItem)

                            },
                            initiallyExpanded = false,
                            // To add padding between cards inside the expandable list
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
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
                onNavigateToSuggestions = {},
                onToggleLanguage = {},
                onGroceryItemCheckBoxClicked = {},
                onNavigateToGroceryItemDetails = {},
            )
        }
    }
