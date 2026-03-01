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

    override suspend fun addGroceryItem(name: String, quantity: Int, category: String) {
        groceryDao.insertGroceryItem(
            GroceryItem(
                name = name,
                quantity = quantity,
                category = category
            )
        )
    }

    override suspend fun deleteGroceryItem(item: GroceryItem) = groceryDao.deleteGroceryItem(item)

    override suspend fun deleteGroceryItemById(id: Int) = groceryDao.deleteGroceryItemById(id)

    override suspend fun updateGroceryItem(item: GroceryItem) = groceryDao.updateItem(item)

    override suspend fun updateItemChecked(id: Int, isChecked: Boolean) = groceryDao.updateItemChecked(id, isChecked)

    override suspend fun toggleItemChecked(id: Int) = groceryDao.toggleItemChecked(id)

    override suspend fun clearAllGroceryItems()  = groceryDao.clearAll()

    override suspend fun clearCompleted() = groceryDao.clearCompleted()

}