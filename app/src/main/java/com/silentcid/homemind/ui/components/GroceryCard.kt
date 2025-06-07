package com.silentcid.homemind.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview // Import Preview
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.ui.theme.HomeMindTheme // Import your app theme

@Composable
fun GroceryItemCard(item: GroceryItem,
                    onCheckedChange: (GroceryItem) -> Unit,
                    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            // The horizontal padding here is for the card's position relative to its container's width.
            // Vertical padding for spacing *between* cards is typically applied by the parent.
            .padding(horizontal = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), // Internal padding for the card's content
            verticalAlignment = Alignment
                .CenterVertically,
            horizontalArrangement =
                Arrangement.SpaceBetween

        ) {
            Column(modifier = Modifier.weight(1f))
            { // Internal padding for the card's content
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Quantity: ${item.quantity}", // Make sure your GroceryItem has 'quantity'
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { isChecked ->
                    onCheckedChange(item)
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "Single Grocery Item Card")
@Composable
fun GroceryItemCardPreview() {
    // Assuming your GroceryItem data class can be instantiated like this:
    // You might need to adjust based on your actual GroceryItem definition.
    val sampleItem = GroceryItem(
        id = 1, // Or whatever fields your GroceryItem requires
        name = "Organic Bananas",
        quantity = 6,
        isChecked = false, // Add all required fields for GroceryItem
    )
    HomeMindTheme { // It's good practice to wrap previews in your app's theme
        GroceryItemCard(
            item = sampleItem,
            onCheckedChange = {},
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, name = "Multiple Grocery Item Cards (simulation of spacing)")
@Composable
fun MultipleGroceryItemCardsPreview() {
    val sampleItem1 = GroceryItem(id = 1, name = "Whole Wheat Bread", quantity = 1, isChecked = false)
    val sampleItem2 = GroceryItem(id = 2, name = "Free-Range Eggs", quantity = 12, isChecked = false)

    HomeMindTheme {
        Column(modifier = Modifier.padding(16.dp)) { // Simulate a parent container
            GroceryItemCard(
                item = sampleItem1,
                // This simulates the padding a parent (like LazyColumn item or ExpandableGroceryList) would add
                modifier = Modifier,
                onCheckedChange = {}
            )
            GroceryItemCard(
                item = sampleItem2,
                // This simulates the padding a parent would add
                modifier = Modifier,
                onCheckedChange = {}
            )
        }
    }
}