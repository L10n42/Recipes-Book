package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DragIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.FieldDefaults

@Composable
fun DefaultComponentsCard(
    onRemove: () -> Unit,
    onDrag: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = FieldDefaults.shape
            )
            .clip(FieldDefaults.shape)
            .clickable(onClick = onClick)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDrag) {
                Icon(
                    imageVector = Icons.Rounded.DragIndicator,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = stringResource(R.string.item_drag_indicator)
                )
            }

            content()
        }

        HorizontalSpace(4.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardDivider()

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = stringResource(R.string.remove_item_button)
                )
            }
        }
    }
}

@Composable
private fun CardDivider(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = Modifier
            .width(1.dp)
            .fillMaxHeight(0.5f)
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            )
            .then(modifier)
    )
}