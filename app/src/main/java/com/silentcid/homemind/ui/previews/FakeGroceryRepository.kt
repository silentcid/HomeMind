package com.silentcid.homemind.ui.previews

import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.repository.GroceryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// Used to Mock GroceryViewModel for Compose Preview Purposes

class FakeGroceryRepository : GroceryRepository {
    private val fakeItems = listOf(
        GroceryItem(id = 1, name = "Bananas", quantity = 3),
        GroceryItem(id = 2, name = "Milk", quantity = 1),
        GroceryItem(id = 3, name = "Bread", quantity = 2),
    )


    override fun getAllGroceryItems(): Flow<List<GroceryItem>>  = flowOf(fakeItems)

    override suspend fun addGroceryItem(item: GroceryItem) {
        // No-Op
    }

    override suspend fun deleteGroceryItem(item: GroceryItem) {
        // No-Op
    }

    override suspend fun updateGroceryItem(item: GroceryItem) {
        // No-Op
    }

    override suspend fun clearAllGroceryItems() {
        // No-Op
    }
}