package com.silentcid.homemind.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.silentcid.homemind.ai.GeminiManager
import com.silentcid.homemind.ui.components.BottomNavTab
import com.silentcid.homemind.ui.components.BottomNavigationBar
import com.silentcid.homemind.ui.screen.dashboard.DashboardScreen
import com.silentcid.homemind.ui.screen.dashboard.DashboardViewModel
import com.silentcid.homemind.ui.screen.grocery.GroceryListScreen
import com.silentcid.homemind.ui.screen.grocery.GroceryListViewModel
import com.silentcid.homemind.ui.screen.grocerydetails.GroceryDetailsRoute
import com.silentcid.homemind.ui.screen.settings.SettingsScreen
import com.silentcid.homemind.ui.screen.suggestions.SuggestionsScreenNew
import com.silentcid.homemind.ui.screen.suggestions.SuggestionsViewModel
import kotlinx.serialization.Serializable

@Serializable
data object DashboardRouteKey : NavKey

@Serializable
data object GroceriesRouteKey : NavKey

@Serializable
data class GroceryDetailsRouteKey(val groceryItemId: Int) : NavKey

@Serializable
data object SuggestionsRouteKey : NavKey

@Serializable
data object SettingsRouteKey : NavKey

@Composable
fun AppNavigation(
    backStack: SnapshotStateList<Any>,
    geminiManager: GeminiManager,
    currentLanguage: String,
    currentTheme: String,
    onLanguageChange: (String) -> Unit,
    onThemeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTab by remember(backStack.lastOrNull()) {
        mutableStateOf(
            when (backStack.lastOrNull()) {
                is DashboardRouteKey -> BottomNavTab.HOME
                is GroceriesRouteKey -> BottomNavTab.GROCERIES
                is SuggestionsRouteKey -> BottomNavTab.SUGGESTIONS
                is SettingsRouteKey -> BottomNavTab.SETTINGS
                else -> BottomNavTab.HOME
            }
        )
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            val showBottomBar = backStack.lastOrNull() !is GroceryDetailsRouteKey
            if (showBottomBar) {
                BottomNavigationBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        currentTab = tab
                        val newKey = when (tab) {
                            BottomNavTab.HOME -> DashboardRouteKey
                            BottomNavTab.GROCERIES -> GroceriesRouteKey
                            BottomNavTab.SUGGESTIONS -> SuggestionsRouteKey
                            BottomNavTab.SETTINGS -> SettingsRouteKey
                        }
                        if (backStack.lastOrNull() != newKey) {
                            backStack.clear()
                            backStack.add(newKey)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        val entryProvider = entryProvider<Any> {
            entry<DashboardRouteKey> {
                val viewModel: DashboardViewModel = hiltViewModel()
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigate = { tab ->
                        currentTab = tab
                        val key = when (tab) {
                            BottomNavTab.HOME -> DashboardRouteKey
                            BottomNavTab.GROCERIES -> GroceriesRouteKey
                            BottomNavTab.SUGGESTIONS -> SuggestionsRouteKey
                            BottomNavTab.SETTINGS -> SettingsRouteKey
                        }
                        backStack.clear()
                        backStack.add(key)
                    },
                    onNavigateToDetails = { itemId ->
                        backStack.add(GroceryDetailsRouteKey(itemId))
                    }
                )
            }
            entry<GroceriesRouteKey> {
                val viewModel: GroceryListViewModel = hiltViewModel()
                GroceryListScreen(
                    viewModel = viewModel,
                    currentLanguage = currentLanguage,
                    onNavigateToSuggestions = {
                        currentTab = BottomNavTab.SUGGESTIONS
                        backStack.clear()
                        backStack.add(SuggestionsRouteKey)
                    },
                    onNavigateToDetails = { itemId: Int ->
                        backStack.add(GroceryDetailsRouteKey(itemId))
                    }
                )
            }
            entry<GroceryDetailsRouteKey> { navKey ->
                GroceryDetailsRoute(
                    groceryItemId = navKey.groceryItemId,
                    onBack = {
                        if (backStack.size > 1) {
                            backStack.removeAt(backStack.size - 1)
                        }
                    },
                    contextForResources = androidx.compose.ui.platform.LocalContext.current
                )
            }
            entry<SuggestionsRouteKey> {
                val viewModel: SuggestionsViewModel = hiltViewModel()
                SuggestionsScreenNew(
                    viewModel = viewModel,
                    currentLanguage = currentLanguage
                )
            }
            entry<SettingsRouteKey> {
                SettingsScreen(
                    currentLanguage = currentLanguage,
                    currentTheme = currentTheme,
                    onLanguageChange = onLanguageChange,
                    onThemeChange = onThemeChange,
                    geminiManager = geminiManager
                )
            }
        }

        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            NavDisplay(
                backStack = backStack,
                entryProvider = entryProvider,
                onBack = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                        true
                    }
                }
            )
        }
    }
}
