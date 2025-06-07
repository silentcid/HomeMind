package com.silentcid.homemind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf // For Navigation 3 backStack
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.silentcid.homemind.navigation.AppNavigation // Import your AppNavigation composable
import com.silentcid.homemind.navigation.HomeRouteKey
import com.silentcid.homemind.ui.theme.HomeMindTheme
import com.silentcid.homemind.utils.LocaleManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the default language of the device
        val defaultLanguage: String = resources.configuration
            .locales[LocaleManager.DEFAULT_LOCALE_INDEX]
            .language // Assuming LocaleManager.DEFAULT_LOCALE_INDEX is 0 or correctly defined

        // Store the current language. If this updates it will trigger recomposition.
        val currentLanguage = mutableStateOf(defaultLanguage)



        setContent {

            // Define and remember the backstack
            val backStack = remember { mutableStateListOf<Any>(HomeRouteKey) }

            // This will provide a new Context whenever currentLanguage.value changes,
            // triggering recomposition of consumers like AppNavigation -> HomeRoute.
            val localizedContext = remember(currentLanguage.value) {
                LocaleManager.setLocale(this, currentLanguage.value)
            }

            // Used to switch the Language on the fly and force recomposition.
            val toggleLanguage = remember {
                {
                    currentLanguage.value = if (currentLanguage.value == LocaleManager.ENGLISH) {
                        LocaleManager.JAPANESE
                    } else {
                        LocaleManager.ENGLISH
                    }
                }
            }

            HomeMindTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call your AppNavigation composable which now handles navigation
                    AppNavigation(
                        backStack = backStack,
                        localizedContext = localizedContext,
                        onToggleLanguage = toggleLanguage,
                    )
                }
            }
        }
    }
}