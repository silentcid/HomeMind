package com.silentcid.homemind

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import com.silentcid.homemind.ai.GeminiManager
import com.silentcid.homemind.navigation.AppNavigation
import com.silentcid.homemind.navigation.DashboardRouteKey
import com.silentcid.homemind.viewmodels.SettingsViewModel
import com.silentcid.homemind.ui.theme.HomeMindTheme
import com.silentcid.homemind.utils.LocaleManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var geminiManager: GeminiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultLanguage: String = resources.configuration
            .locales[LocaleManager.DEFAULT_LOCALE_INDEX]
            .language

        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val savedLanguage by settingsViewModel.language.collectAsState()
            val savedTheme by settingsViewModel.theme.collectAsState()

            val currentLanguage = savedLanguage ?: defaultLanguage
            val currentTheme = savedTheme

            // LocaleManager.setLocale now updates the Activity's resources directly.
            // This ensures Dialogs and BottomSheets (which use Activity resources) are localized.
            val localizedContext = remember(currentLanguage) {
                LocaleManager.setLocale(this, currentLanguage)
            }

            val isDarkTheme = when (currentTheme) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }

            val layoutDirection = if (localizedContext.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                LayoutDirection.Rtl
            } else {
                LayoutDirection.Ltr
            }

            val backStack = remember { mutableStateListOf<Any>(DashboardRouteKey) }

            key(currentLanguage) {
                CompositionLocalProvider(
                    LocalContext provides localizedContext,
                    LocalConfiguration provides Configuration(localizedContext.resources.configuration),
                    LocalLayoutDirection provides layoutDirection
                ) {
                    HomeMindTheme(darkTheme = isDarkTheme) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavigation(
                                backStack = backStack,
                                geminiManager = geminiManager,
                                currentLanguage = currentLanguage,
                                currentTheme = currentTheme,
                                onLanguageChange = { settingsViewModel.setLanguage(it) },
                                onThemeChange = { settingsViewModel.setTheme(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}
