package com.silentcid.homemind
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.ui.screen.home.HomeScreen
import com.silentcid.homemind.ui.theme.HomeMindTheme
import com.silentcid.homemind.utils.LocaleManager

class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the default language of the device
        val defaultLanguage: String = resources.configuration
            .locales[LocaleManager.DEFAULT_LOCALE_INDEX]
            .language


        // Store the current language. If this updates it will trigger recomposition in the
        // localizedContext
        var currentLanguage = mutableStateOf(defaultLanguage)

        setContent {


            val localizedContext = remember (currentLanguage.value) {
                LocaleManager.setLocale(this, currentLanguage.value)
            }

            CompositionLocalProvider(LocalContext provides localizedContext) {

                HomeMindTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // Dummy grocery item list
                        val dummyItems = listOf(
                            GroceryItem(0, "StrawBerry", 2, false),
                            GroceryItem(1, "Bread", 1, false),
                            GroceryItem(2, "Ice Cream", 3, false),
                            GroceryItem(3, "Coca Cola", 5, false),
                            GroceryItem(3, "nuggets", 15, false)

                        )

                        HomeScreen(
                            groceryItems = dummyItems,
                            onNavigateToGrocery = {},
                            onNavigateToSuggestions = {},
                            onToggleLanguage = {
                                currentLanguage.value = if(currentLanguage.value ==
                                    LocaleManager.ENGLISH)
                                    LocaleManager.JAPANESE
                                else
                                    LocaleManager.ENGLISH
                            },
                        )
                    }
                }
            }
        }
    }
}
