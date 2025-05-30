package com.silentcid.homemind
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.ui.screen.home.HomeScreen
import com.silentcid.homemind.ui.theme.HomeMindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeMindTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Dummy grocery item list
                    val dummyItems = listOf(
                        GroceryItem(0,"StrawBerry", 2, false),
                        GroceryItem(1,"Bread", 1, false),
                        GroceryItem(2,"Ice Cream", 3, false),
                        GroceryItem(3,"Coca Cola", 5, false ),
                        GroceryItem(3,"nuggets", 15, false )

                    )

                    HomeScreen(
                        groceryItems = dummyItems,
                        onNavigateToGrocery = {},
                        onNavigateToSuggestions = {}
                    )
                }
            }
        }
    }
}
