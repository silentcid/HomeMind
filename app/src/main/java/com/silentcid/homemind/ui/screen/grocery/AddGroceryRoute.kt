package com.silentcid.homemind.ui.screen.grocery

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.silentcid.homemind.data.models.PendingGroceryEntry
import com.silentcid.homemind.ui.screen.addgrocery.AddGroceryScreen
import com.silentcid.homemind.viewmodels.GroceryViewModel

@Composable
fun AddGroceryRoute(
    onBack: () -> Unit,
    groceryViewModel: GroceryViewModel = hiltViewModel(),
    contextForResource: Context,
) {
    // Manage a list of pending entries, start with one empty entry
    val pendingEntries = remember { mutableStateListOf(PendingGroceryEntry()) }

    CompositionLocalProvider(LocalContext provides contextForResource) {
        AddGroceryScreen(
            entries = pendingEntries,
            onItemNameChanged = { index, newName ->
                if (index in pendingEntries.indices) {
                    pendingEntries[index] = pendingEntries[index].copy(name = newName)
                }
            },
            onItemQuantityChanged = { index, newQuantity ->
                if (index in pendingEntries.indices) {
                    pendingEntries[index] = pendingEntries[index].copy(quantity = newQuantity)
                }
            },
            onRemoveEntryClicked = { index ->
                if (index in pendingEntries.indices) {
                    pendingEntries.removeAt(index)
                    // If all entries are removed, add a fresh empty one back
                    if (pendingEntries.isEmpty()) {
                        pendingEntries.add(PendingGroceryEntry())
                    }
                }
            },
            onAddNewEntryClicked = {
                pendingEntries.add(PendingGroceryEntry())
            },
            onSaveAllClicked = {
                val itemsToAdd = pendingEntries.filter { it.name.isNotBlank() }
                itemsToAdd.forEach { entry ->
                    val quantity = entry.quantity.toIntOrNull() ?: 1
                    groceryViewModel.addItem(name = entry.name.trim(), quantity = quantity)
                }
                Log.d("AddGroceryRoute", "Saved ${itemsToAdd.size} items.")

                // After saving, either navigate back or reset to a single empty entry
                // Option 1: Navigate back
                onBack()
                // Option 2: Reset for more entries
                // pendingEntries.clear()
                // pendingEntries.add(PendingGroceryEntry())
            },
            onBackClicked = onBack
        )
    }
}

// Helper function if you need to manage SnapshotStateList directly for recomposition
// (though direct modification of SnapshotStateList items as above often works)
fun <T> SnapshotStateList<T>.update(index: Int, item: T) {
    if (index >= 0 && index < this.size) {
        this.removeAt(index)
        this.add(index, item)
    }
}