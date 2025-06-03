package com.silentcid.homemind.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.silentcid.homemind.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silentcid.homemind.data.models.GroceryItem

@Composable
fun ExpandableGroceryList(
    @StringRes title: Int,
    groceryList: List<GroceryItem>,
    onCheckChange: (groceryItem: GroceryItem) -> Unit,
    modifier: Modifier = Modifier,
    initiallyExpanded: Boolean = false
) {
    var listExpanded by remember { mutableStateOf(initiallyExpanded) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (listExpanded) 180f else 0f,
        label = "caratRotation"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { listExpanded = !listExpanded },
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (listExpanded) "Collapse $title" else "Expand $title",
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }

        AnimatedVisibility(
            visible = listExpanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Column(
                // To add padding between cards inside the expandable list
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                groceryList.forEach { item ->
                    GroceryItemCard(
                        item = item,
                        onCheckedChange = { changedGroceryItem ->
                            onCheckChange(changedGroceryItem)
                        },
                        modifier = modifier,
                    )
                }
            }
        }
    }
}

// --- Preview ---
// Ensure your GroceryItem model can be instantiated for the preview.

@Preview(showBackground = true, name = "Expandable List with GroceryItemCards")
@Composable
fun ExpandableGroceryListWithCardsPreview() {
    // Assuming GroceryItem can be created like this for preview:
    val sampleItems = listOf(
        GroceryItem(id = 1, name = "Apples", quantity = 2, isChecked = false),
        GroceryItem(id = 2, name = "Organic Milk", quantity = 1, isChecked = false),
        GroceryItem(id = 3, name = "Sourdough Bread", quantity = 1, isChecked = false)
    )

    var itemsForPreview by remember { mutableStateOf(sampleItems) }

    MaterialTheme {
        ExpandableGroceryList(
            title = R.string.welcome_text,
            groceryList = itemsForPreview,
            onCheckChange = { changedItem ->
                //Flow the check change up the chain
                itemsForPreview = itemsForPreview.map { groceryItem ->
                    if (groceryItem.id == changedItem.id) groceryItem.copy(isChecked = !groceryItem.isChecked)
                    else groceryItem
                }
                println("Check changed for: ${changedItem.name}")
            },
            initiallyExpanded = true,
            modifier = Modifier.padding(8.dp)
        )
    }
}