package com.silentcid.homemind.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.silentcid.homemind.R
import com.silentcid.homemind.data.models.GroceryItem
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
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
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = dimensionResource(R.dimen.screen_margins)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.weight(1f)) // 👈 Pushes the card down from top

                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .defaultMinSize(minWidth = 160.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_margins))
                ) {
                    Text(
                        text = stringResource(R.string.welcome_text),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = stringResource(R.string.grocery_count, groceryItems.size),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Button(onClick = onNavigateToGrocery) {
                        Text(stringResource(R.string.grocery_button))
                    }

                    Button(onClick = onNavigateToSuggestions) {
                        Text(stringResource(R.string.suggestion_button))
                    }
                }

                Spacer(modifier = Modifier.weight(2f)) // 👈 Allows space for future bottom content
            }
        }

    )
}


@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Composable
fun ButtonColorPreview() {
    HomeMindTheme(darkTheme = true) {
        Button(onClick = {}) {
            Text("Custom Themed Button")
        }
    }
}



@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode")
@Composable
fun HomeScreenPreview() {
    HomeMindTheme(darkTheme = true
    ) {
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