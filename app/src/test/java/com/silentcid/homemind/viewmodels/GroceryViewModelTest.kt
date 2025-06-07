package com.silentcid.homemind.viewmodels

import com.silentcid.homemind.MainCoroutineRule
import com.silentcid.homemind.core.utils.TestDispatcherProvider
import com.silentcid.homemind.data.models.GroceryItem
import com.silentcid.homemind.domain.models.UserFacingError
import com.silentcid.homemind.repository.GroceryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import app.cash.turbine.test
import com.silentcid.homemind.R
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.doAnswer

@ExperimentalCoroutinesApi
class GroceryViewModelTest {

    // Uses a test dispatcher from the MainCoroutineRule
    // instead of the normal Dispatchers.Main
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var testDispatcherProvider: TestDispatcherProvider
    private lateinit var mockRepository: GroceryRepository
    private lateinit var viewModel: GroceryViewModel

    // simulate the repository returning a flow of grocery items.
    // First with an empty list, then with a list of grocery items.
    // followed by an updated list.
    private lateinit var grocerItemsFlowFromRepo:
            MutableSharedFlow<List<GroceryItem>>


    private val initialTestGroceryItems = listOf(
        GroceryItem(id = 0, "StrawBerry", 2, false),
        GroceryItem(id = 1, "Bread", 1, false),
        GroceryItem(id = 2, "Ice Cream", 3, false),
        GroceryItem(id = 3, "Coca Cola", 5, false),
        GroceryItem(id = 4, "nuggets", 15, false),
    )


    @Before
    fun setUp() {
        // Initialize with the StandardTestDispatcher from the rule
        testDispatcherProvider = TestDispatcherProvider(mainCoroutineRule.testDispatcher)
        mockRepository = mock()
        grocerItemsFlowFromRepo =
            MutableSharedFlow(replay = 1) // replay = 1 to cache last emitted value

        // Default happy path for repository getAllGroceryItems()
        // The ViewModel's init block calls loadInitialItems(), which doesn't directly call
        // repository.getAllGroceryItems(), so we don't strictly need to mock it here
        // for the init {} block itself, but it's good practice for loadGroceryItems() calls.
        whenever(mockRepository.getAllGroceryItems()).thenReturn(grocerItemsFlowFromRepo)

        // Initialize the ViewModel AFTER mocks and testDispatcherProvider are set up
        viewModel = GroceryViewModel(mockRepository, testDispatcherProvider)
        // Note: The ViewModel's init block populates initialDummyItems if _groceryItems is empty.
        // This will be the case when the ViewModel is first created.
    }

    @Test
    fun `init - loads initial dummy items correctly`() = runTest(mainCoroutineRule.testDispatcher) {
        // The ViewModel's init block should have loaded the dummy items.
        // We use Turbine to test the StateFlow.
        viewModel.groceryItems.test {
            assertEquals(
                "Initial dummy items should be loaded",
                initialTestGroceryItems,
                awaitItem()
            )
            cancelAndConsumeRemainingEvents() // Ensure no other unexpected emissions
        }
        // Verify that the repository was not called to get all items during this specific init path,
        // as it loads dummy data directly if the internal list is empty.
        verify(mockRepository, never()).getAllGroceryItems()
    }

    @Test
    fun `loadGroceryItems - success - updates groceryItems StateFlow from repository`() =
        runTest(mainCoroutineRule.testDispatcher) {
            val repoItems = listOf(GroceryItem(10, "Milk from Repo", 1, false))

            // Since init already populated dummy items, loadGroceryItems will overwrite them.
            viewModel.loadGroceryItems() // Call the function that uses the repository
            advanceUntilIdle() // Let the coroutine in loadGroceryItems start collecting

            // Emit items from the repository's flow
            grocerItemsFlowFromRepo.emit(repoItems)
            advanceUntilIdle() // Ensure the collect block in loadGroceryItems processes the emission

            viewModel.groceryItems.test {
                // The flow might have replayed its last value (if replay > 0) or started fresh.
                // In our case, `replay = 1` for groceryItemsFlowFromRepo, and ViewModel's
                // _groceryItems is a StateFlow, so it will hold the latest.
                assertEquals(
                    "Grocery items should be updated from repository",
                    repoItems,
                    awaitItem()
                )
                cancelAndConsumeRemainingEvents()
            }
            verify(mockRepository).getAllGroceryItems() // Verify interaction
        }

    @Test
    fun `loadGroceryItems - repository error - updates errorState`() =
        runTest(mainCoroutineRule.testDispatcher) {
            val ioExceptionMessage =
                "Failed to load from repository" // This is the message for the IOException itself, useful for logging

            // Make the repository throw an IOException
            whenever(mockRepository.getAllGroceryItems()).thenReturn(flow {
                throw IOException(
                    ioExceptionMessage
                )
            })

            viewModel.loadGroceryItems()
            advanceUntilIdle()

            viewModel.errorState.test {
                val error = awaitItem() // This should be UserFacingError.GeneralError

                // 1. Assert the type is correct
                assertTrue(
                    "Error state should be UserFacingError.GeneralError because IOException maps to fallback",
                    error is UserFacingError.GeneralError
                )

                // 2. Cast to GeneralError to access its properties
                val generalError = error as UserFacingError.GeneralError

                // 3. Assert the properties match the default fallback values from handleFailure
                assertEquals(
                    "Error title should match the default general error title resource ID",
                    R.string.general_error_title, // Expected Int resource ID
                    generalError.title
                )
                assertEquals(
                    "Error description should match the default general error description resource ID",
                    R.string.general_error_description, // Expected Int resource ID
                    generalError.description
                )

                cancelAndConsumeRemainingEvents()
            }
            verify(mockRepository).getAllGroceryItems()
        }

    @Test
    fun `addItem - success - calls repository addGroceryItem`() =
        runTest(mainCoroutineRule.testDispatcher) {
            val itemName = "New Cheese"
            val itemQuantity = 3

            // Mock the repository's addGroceryItem to do nothing (successful operation)
            // We use doAnswer {} for suspend functions returning Unit.
            whenever(mockRepository.addGroceryItem(any())).doAnswer { }

            viewModel.addItem(itemName, itemQuantity)
            advanceUntilIdle() // Allow the coroutine in addItem to complete

            // Verify that repository.addGroceryItem was called with an item matching name and quantity.
            // We use 'any()' for the item because the ID is generated by Room/Repo and we can't predict it.
            // A more precise matcher could be created if needed.
            verify(mockRepository).addGroceryItem(
                check { groceryItem ->
                    assertEquals(itemName, groceryItem.name)
                    assertEquals(itemQuantity, groceryItem.quantity)
                    assertEquals(false, groceryItem.isChecked)
                }
            )
        }

    @Test
    fun `addItem - repository error - updates errorState to GeneralError`() =
        runTest(mainCoroutineRule.testDispatcher) {
            val itemName = "Error Item"
            val itemQuantity = 1 // Assuming addItem takes a quantity, add it here. If not, remove.
            val ioExceptionMessage =
                "Failed to add item" // This is the raw exception message, useful for logging/debugging

            whenever(mockRepository.addGroceryItem(any())).doAnswer {
                throw IOException(ioExceptionMessage)
            }

            // Call the ViewModel method that adds an item
            // Ensure you're passing all required parameters to viewModel.addItem
            viewModel.addItem(itemName, itemQuantity) // Pass quantity if your method requires it
            advanceUntilIdle() // Allow any coroutines launched by addItem to complete

            // Assert on the errorState using Turbine
            viewModel.errorState.test {
                val error = awaitItem() // Get the emitted error

                // 1. Assert that the error is of the expected type (GeneralError)
                assertTrue(
                    "Error state should be UserFacingError.GeneralError due to IOException and default fallback",
                    error is UserFacingError.GeneralError
                )

                // 2. Cast to GeneralError to access its properties
                val generalError = error as UserFacingError.GeneralError

                // 3. Assert the properties match the default fallback values from handleFailure
                assertEquals(
                    "Error title should match the default general error title resource ID",
                    R.string.general_error_title, // Expected Int resource ID from your R file
                    generalError.title
                )
                assertEquals(
                    "Error description should match the default general error description resource ID",
                    R.string.general_error_description, // Expected Int resource ID from your R file
                    generalError.description
                )

                // Ensure no other unexpected errors are emitted
                cancelAndConsumeRemainingEvents()
            }

            // Verify that the repository's addGroceryItem method was indeed called
            verify(mockRepository).addGroceryItem(
                check { groceryItemArgument ->
                    assertEquals(
                        "Item name passed to repository should match",
                        itemName,
                        groceryItemArgument.name
                    )
                    assertEquals(
                        "Item quantity passed to repository should match",
                        itemQuantity,
                        groceryItemArgument.quantity
                    )
                }
            )
        }

    @Test
    fun `deleteItem - success - calls repository deleteGroceryItem`() =
        runTest(mainCoroutineRule.testDispatcher) {
            val itemToDelete = GroceryItem(1, "Item to Delete", 1)
            whenever(mockRepository.deleteGroceryItem(any())).doAnswer { }

            viewModel.deleteItem(itemToDelete)
            advanceUntilIdle()

            verify(mockRepository).deleteGroceryItem(any())
        }
}