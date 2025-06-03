package com.silentcid.homemind.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.silentcid.homemind.ui.screen.grocery.AddGroceryRoute
import com.silentcid.homemind.ui.screen.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRouteKey: NavKey
@Serializable
data object GroceryAddRouteKey: NavKey
@Serializable
data class GroceryDetailsRouteKey(val groceryItemId: Int): NavKey
@Serializable
data object SuggestionsRouteKey: NavKey


@Composable
fun AppNavigation(
    backStack: SnapshotStateList<Any>, // Pass the mutable backstack
    localizedContext: Context, // Pass the localized context
    onToggleLanguage: () -> Unit,
) {

    // Define the EntryProvider for the backstack and map key to the composable content
    val entryProvider = entryProvider<Any> {
        entry<HomeRouteKey> {
            HomeRoute(contextForResources = localizedContext,
                onNavigateToGrocery = { backStack.add(GroceryAddRouteKey)},
                onNavigateToSuggestions = { backStack.add(SuggestionsRouteKey)},
                onNavigateToGroceryItemDetails = { // No-Op not implemented yet
                },
                onToggleLanguage = onToggleLanguage)
        }
        entry<GroceryAddRouteKey> {
            AddGroceryRoute(
                onBack = { backStack.removeLastOrNull() }
            )
        }

    }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider,
        onBack = {
            Log.d("AppNavigation", "NavDisplay onBack triggered.")
            val removed = backStack.removeLastOrNull()
            Log.d("AppNavigation", "Backstack after NavDisplay onBack: ${backStack.joinToString()}")
            removed != null
        },
    )
}

