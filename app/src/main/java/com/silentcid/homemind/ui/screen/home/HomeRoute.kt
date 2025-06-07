package com.silentcid.homemind.ui.screen.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.silentcid.homemind.viewmodels.GroceryViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.silentcid.homemind.data.models.CarouselItem


@Composable
fun HomeRoute(
    viewModel: GroceryViewModel = hiltViewModel(),
    contextForResources: Context,
    onNavigateToGrocery: () -> Unit,
    onNavigateToSuggestions: () -> Unit,
    onToggleLanguage: () -> Unit,
    onNavigateToGroceryItemDetails: () -> Unit,
) {
    val groceryItems by viewModel.groceryItems.collectAsState()

    CompositionLocalProvider(LocalContext provides contextForResources) {
        HomeScreen(
            groceryItems = groceryItems,
            onNavigateToGrocery =  onNavigateToGrocery,
            onNavigateToSuggestions = onNavigateToSuggestions,
            onToggleLanguage = onToggleLanguage,
            onGroceryItemCheckBoxClicked = { item ->
                Log.d("HomeRoute", "onGroceryItemCheckBoxClicked called for '${item.name}'.")
                viewModel.updateItemChecked(item)
            },
            onNavigateToGroceryItemDetails = onNavigateToGroceryItemDetails,

            )
    }
}