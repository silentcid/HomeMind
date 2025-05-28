package com.silentcid.homemind.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.silentcid.homemind.viewmodels.GroceryViewModel
import androidx.compose.runtime.getValue

@Composable
fun HomeRoute(
    viewModel: GroceryViewModel = hiltViewModel(),
    onNavigateToGrocery: () -> Unit,
    onNavigateToSuggestions: () -> Unit
) {
    val groceryItems by viewModel.groceryItems.collectAsState()

    HomeScreen(
        groceryItems = groceryItems,
        onNavigateToGrocery = onNavigateToGrocery,
        onNavigateToSuggestions = onNavigateToSuggestions
    )
}