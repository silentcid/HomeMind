package com.silentcid.homemind.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.silentcid.homemind.core.utils.DispatcherProvider
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.repository.GroceryRepository
import androidx.lifecycle.viewModelScope
import com.silentcid.homemind.core.retrofit.utils.handleFailure
import com.silentcid.homemind.core.retrofit.utils.logFailure
import com.silentcid.homemind.core.retrofit.utils.mapErrors
import com.silentcid.homemind.domain.models.UserFacingError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject
import kotlin.collections.listOf

@HiltViewModel
class GroceryViewModel @Inject constructor(
    private val  repository: GroceryRepository,
    private val dispatcherProvider: DispatcherProvider

): ViewModel() {

    private val _groceryItems = MutableStateFlow(emptyList<GroceryItem>())
    val groceryItems: StateFlow<List<GroceryItem>> = _groceryItems.asStateFlow()

    // To show errors to the user.
    val errorState = MutableStateFlow<UserFacingError>(UserFacingError.NoError)


    init {
        loadInitialItems()
    }

    // Temporary solution to loading fake data. This will be replaced with more Room Implementation
    private fun loadInitialItems() {
        if (_groceryItems.value.isEmpty()) {
            val initialItems = listOf(
                GroceryItem(0, "StrawBerry", 2, false),
                GroceryItem(1, "Bread", 1, false),
                GroceryItem(2, "Ice Cream", 3, false),
                GroceryItem(3, "Coca Cola", 5, false),
                GroceryItem(3, "nuggets", 15, false)
            )
            Log.d("GroceryViewModel", "Loaded initial dummy items into ViewModel.")
            _groceryItems.value = initialItems
        }

    }

    fun loadGroceryItems() {
        viewModelScope.launch(dispatcherProvider.IO) {
            runCatching {
                repository.getAllGroceryItems().collect { groceryItems ->
                    _groceryItems.value = groceryItems

                }
            }.mapErrors()
                .logFailure()
                .handleFailure(errorState = errorState)

        }
    }

    fun addItem(name: String, quantity: Int = 1) {
        viewModelScope.launch(dispatcherProvider.IO) {
            val item = GroceryItem(name = name, quantity = quantity)
            runCatching { repository.addGroceryItem(item) }
                .mapErrors()
                .logFailure()
                .handleFailure(errorState)
        }
    }

    fun deleteItem(item: GroceryItem) {
        viewModelScope.launch(dispatcherProvider.IO) {
            runCatching { repository.deleteGroceryItem(item) }
                .mapErrors()
                .logFailure()
                .handleFailure(errorState)
        }
    }

    fun updateItem(item: GroceryItem) {
        viewModelScope.launch(dispatcherProvider.IO) {
            runCatching { repository.updateGroceryItem(item) }
                .mapErrors()
                .logFailure()
                .handleFailure(errorState)
        }
    }

    //temporary placeholder for updateItem function until room is fully implemented
    fun updateItemChecked(item: GroceryItem) {
        viewModelScope.launch(dispatcherProvider.IO) {
            _groceryItems.update { currentGroceryItems ->
                currentGroceryItems.map { groceryItem ->
                    if (groceryItem.id == item.id) {
                        groceryItem.copy(isChecked = !groceryItem.isChecked)
                    } else {
                        groceryItem
                    }

                }
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch(dispatcherProvider.IO) {
            runCatching { repository.clearAllGroceryItems() }
                .mapErrors()
                .logFailure()
                .handleFailure(errorState)
        }
    }

    fun clearErrors() {
        errorState.value = UserFacingError.NoError
    }

}