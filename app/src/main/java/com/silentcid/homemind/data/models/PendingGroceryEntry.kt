package com.silentcid.homemind.data.models

import java.util.UUID

data class PendingGroceryEntry(
    val id: UUID = UUID.randomUUID(), // Unique identifier for stable keys with a LazyColumn
    var name: String = "",
    var quantity: String = "1" // String for validation
)
