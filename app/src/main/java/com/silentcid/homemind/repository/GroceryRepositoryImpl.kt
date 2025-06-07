package com.silentcid.homemind.repository

import com.silentcid.homemind.data.dao.GroceryDao
import com.silentcid.homemind.data.models.GroceryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroceryRepositoryImpl @Inject constructor(
    private val groceryDao: GroceryDao
): GroceryRepository {

    override fun getAllGroceryItems(): Flow<List<GroceryItem>> = groceryDao.getGroceryList()

    override suspend fun addGroceryItem(item: GroceryItem) = groceryDao.insertGroceryItem(item)

    override suspend fun deleteGroceryItem(item: GroceryItem) = groceryDao.deleteGroceryItem(item)

    override suspend fun updateGroceryItem(item: GroceryItem) = groceryDao.updateItem(item)

    override suspend fun clearAllGroceryItems()  = groceryDao.clearAll()

}