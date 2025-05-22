package com.silentcid.homemind.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silentcid.homemind.data.dao.GroceryDao
import com.silentcid.homemind.data.models.GroceryItem

@Database(
    entities = [GroceryItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun groceryDao(): GroceryDao

}