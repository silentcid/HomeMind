package com.silentcid.homemind.repository


import com.silentcid.homemind.data.models.GroceryItem
import kotlinx.coroutines.flow.Flow


interface GroceryRepository
{
    fun getAllGroceryItems(): Flow<List<GroceryItem>>
    suspend fun addGroceryItem(item: GroceryItem)
    suspend fun addGroceryItem(name: String, quantity: Int, category: String)
    suspend fun deleteGroceryItem(item: GroceryItem)
    suspend fun deleteGroceryItemById(id: Int)
    suspend fun updateGroceryItem(item: GroceryItem)
    suspend fun updateItemChecked(id: Int, isChecked: Boolean)
    suspend fun toggleItemChecked(id: Int)
    suspend fun clearAllGroceryItems()
    suspend fun clearCompleted()
}