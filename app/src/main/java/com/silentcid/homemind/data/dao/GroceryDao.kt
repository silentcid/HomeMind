package com.silentcid.homemind.data.dao

import androidx.room.Query
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.silentcid.homemind.data.models.GroceryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    @Query("SELECT * FROM GROCERY_ITEMS ORDER by id DESC")
    fun getGroceryList(): Flow<List<GroceryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroceryItem(item: GroceryItem)

    @Delete
    suspend fun deleteGroceryItem(item: GroceryItem)

    @Query("DELETE FROM grocery_items WHERE id = :id")
    suspend fun deleteGroceryItemById(id: Int)

    @Update
    suspend fun updateItem(item: GroceryItem)

    @Query("UPDATE grocery_items SET isChecked = :isChecked WHERE id = :id")
    suspend fun updateItemChecked(id: Int, isChecked: Boolean)

    @Query("UPDATE grocery_items SET isChecked = NOT isChecked WHERE id = :id")
    suspend fun toggleItemChecked(id: Int)

    @Query("DELETE FROM grocery_items")
    suspend fun clearAll()

    @Query("DELETE FROM grocery_items WHERE isChecked = 1")
    suspend fun clearCompleted()
}
