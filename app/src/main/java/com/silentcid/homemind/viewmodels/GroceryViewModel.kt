package com.silentcid.homemind.viewmodels

import androidx.lifecycle.ViewModel
import com.silentcid.homemind.core.utils.DispatcherProvider
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.repository.GroceryRepository
import androidx.lifecycle.viewModelScope
import com.silentcid.homemind.core.retrofit.utils.handleFailure
import com.silentcid.homemind.core.retrofit.utils.logFailure
import com.silentcid.homemind.core.retrofit.utils.mapErrors
import com.silentcid.homemind.domain.models.UserFacingError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

class GroceryViewModel @Inject constructor(
    private val  repository: GroceryRepository,
    private val dispatcherProvider: DispatcherProvider

): ViewModel() {

    private val _groceryItems = MutableStateFlow(emptyList<GroceryItem>())
    val groceryItems: StateFlow<List<GroceryItem>> = _groceryItems.asStateFlow()

    // To show errors to the user.
    val errorState = MutableStateFlow<UserFacingError>(UserFacingError.NoError)

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