package com.silentcid.homemind.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.ui.theme.HomeMindTheme

@Composable
fun GroceryItemCard(
    item: GroceryItem,
    onCheckedChange: (GroceryItem) -> Unit,
    onItemClick: (GroceryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onItemClick(item) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Quantity: ${item.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { _ ->
                    onCheckedChange(item)
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "Single Grocery Item Card")
@Composable
fun GroceryItemCardPreview() {
    val sampleItem = GroceryItem(
        id = 1,
        name = "Organic Bananas",
        quantity = 6,
        isChecked = false,
    )
    HomeMindTheme {
        GroceryItemCard(
            item = sampleItem,
            onCheckedChange = {},
            onItemClick = {},
            modifier = Modifier
        )
    }
}
