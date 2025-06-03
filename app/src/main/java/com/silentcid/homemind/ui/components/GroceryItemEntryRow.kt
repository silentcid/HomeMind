package com.silentcid.homemind.ui.components

import com.silentcid.homemind.data.models.PendingGroceryEntry
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete // Example for a remove button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun GroceryItemEntryRow(
    modifier: Modifier = Modifier,
    entry: PendingGroceryEntry,
    onNameChanged: (String) -> Unit,
    onQuantityChanged: (String) -> Unit,
    onRemoveClicked: (() -> Unit)? = null, // Optional: if you want a remove button per row
    isLastEntry: Boolean, // To handle
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = entry.name,
            onValueChange = onNameChanged,
            label = { Text("Item Name") },
            modifier = Modifier.weight(0.6f),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Right) }
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = entry.quantity,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    onQuantityChanged(newValue)
                }
            },
            label = { Text("Qty") },
            modifier = Modifier.weight(0.25f),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = if (isLastEntry) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }, // Might need custom logic if there's a remove button
                onDone = { focusManager.clearFocus() }
            )
        )
        onRemoveClicked?.let {
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = it, modifier = Modifier.weight(0.15f)) {
                Icon(Icons.Default.Delete, contentDescription = "Remove Item Entry")
            }
        }
    }
}