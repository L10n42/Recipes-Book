package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.CustomDropDownMenu

private enum class MenuItem(@StringRes val resId: Int) {
    EDIT(R.string.edit), DELETE(R.string.delete)
}

@Composable
fun RecipeMoreMenu(
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = stringResource(R.string.recipe_more_menu_button)
        )

        CustomDropDownMenu(
            expanded = expanded,
            modifier = Modifier.width(100.dp),
            dismiss = { expanded = false }
        ) {
            MenuItem.values().forEach { item ->
                Item(item) {
                    when (item) {
                        MenuItem.EDIT -> onEdit()
                        MenuItem.DELETE -> onDelete()
                    }
                }
            }
        }
    }
}

@Composable
private fun Item(
    item: MenuItem,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        onClick = onClick
    ) {
        Text(
            text = stringResource(item.resId),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            maxLines = 1
        )
    }
}