package com.silentcid.homemind.ui.screen.addgrocery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.data.models.PendingGroceryEntry
import com.silentcid.homemind.ui.components.GroceryItemEntryRow
import kotlin.collections.lastIndex

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddGroceryScreen(
    entries: List<PendingGroceryEntry>,
    onItemNameChanged: (index: Int, newName: String) -> Unit,
    onItemQuantityChanged: (index: Int, newQuantity: String) -> Unit,
    onRemoveEntryClicked: ((index: Int) -> Unit)? = null, // Make optional if not always shown
    onAddNewEntryClicked: () -> Unit,
    onSaveAllClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Grocery Items") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewEntryClicked) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Add New Item Entry")
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Takes up available space
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(
                    items = entries,
                    key = { _, entry -> entry.id } // Stable keys for performance and animation
                ) { index, entry ->
                    GroceryItemEntryRow(
                        entry = entry,
                        onNameChanged = { newName -> onItemNameChanged(index, newName) },
                        onQuantityChanged = { newQuantity -> onItemQuantityChanged(index, newQuantity) },
                        onRemoveClicked = onRemoveEntryClicked?.let { { it(index) } },
                        isLastEntry = index == entries.lastIndex,
                        modifier = Modifier.animateItem() // Basic animation
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onBackClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = onSaveAllClicked,
                    modifier = Modifier.weight(1f),
                    enabled = entries.any { it.name.isNotBlank() } // Enable if at least one item has a name
                ) {
                    Text("Save All Items")
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // Some spacing at the bottom
        }
    }
}