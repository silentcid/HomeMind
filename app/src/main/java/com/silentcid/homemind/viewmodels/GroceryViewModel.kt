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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import javax.inject.Inject
import kotlin.collections.listOf

@HiltViewModel
class GroceryViewModel @Inject constructor(
    private val repository: GroceryRepository,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    private val _groceryItems = MutableStateFlow(emptyList<GroceryItem>())
    val groceryItems: StateFlow<List<GroceryItem>> = _groceryItems.asStateFlow()

    // To show errors to the user.
    val errorState = MutableStateFlow<UserFacingError>(UserFacingError.NoError)

    init {
        loadInitialItems()
        loadGroceryItems()
    }

    // Temporary solution to loading fake data.
    private fun loadInitialItems() {
        viewModelScope.launch(dispatcherProvider.IO) {
            repository.getAllGroceryItems().collect { items ->
                if (items.isEmpty()) {
                    val initialItems = listOf(
                        GroceryItem(id = 0, name = "StrawBerry", quantity = 2, isChecked = false),
                        GroceryItem(id = 1, name = "Bread", quantity = 1, isChecked = false),
                        GroceryItem(id = 2, name = "Ice Cream", quantity = 3, isChecked = false),
                        GroceryItem(id = 3, name = "Coca Cola", quantity = 5, isChecked = false),
                        GroceryItem(id = 4, name = "nuggets", quantity = 15, isChecked = false),
                    )
                    initialItems.forEach { repository.addGroceryItem(it) }
                }
            }
        }
    }

    fun loadGroceryItems() {
        repository.getAllGroceryItems()
            .onEach { items -> _groceryItems.value = items }
            .launchIn(viewModelScope)
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

    fun updateItemChecked(item: GroceryItem) {
        viewModelScope.launch(dispatcherProvider.IO) {
            repository.updateItemChecked(item.id, !item.isChecked)
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
