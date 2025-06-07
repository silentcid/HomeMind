package com.silentcid.homemind.repository

import com.silentcid.homemind.data.models.GroceryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GroceryRepository
{
    fun getAllGroceryItems(): Flow<List<GroceryItem>>
    suspend fun addGroceryItem(item: GroceryItem)
    suspend fun deleteGroceryItem(item: GroceryItem)
    suspend fun updateGroceryItem(item: GroceryItem)
    suspend fun clearAllGroceryItems()
}