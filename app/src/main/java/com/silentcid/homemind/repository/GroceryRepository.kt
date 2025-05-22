package com.silentcid.homemind.repository

import com.silentcid.homemind.data.dao.GroceryDao
import com.silentcid.homemind.data.models.GroceryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroceryRepository @Inject constructor(
    private val groceryDao: GroceryDao
){
    fun getAllGroceryItems(): Flow<List<GroceryItem>> {
        return groceryDao.getGroceryList()
    }

    suspend fun addGroceryItem(item: GroceryItem) {
        groceryDao.insertGroceryItem(item)
    }

    suspend fun deleteGroceryItem(item: GroceryItem) {
        groceryDao.deleteGroceryItem(item)
    }

    suspend fun updateGroceryItem(item: GroceryItem) {
        groceryDao.updateItem(item)
    }

    suspend fun clearAllGroceryItems() {
        groceryDao.clearAll()
    }

}