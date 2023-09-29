package com.kappdev.recipesbook.categories_feature.presentation.manage_categories.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.core.presentation.common.components.AnimatedComponentCard

@Composable
fun CategoryCard(
    category: String,
    onRemove: () -> Unit,
    onDrag: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    AnimatedComponentCard(
        modifier = modifier,
        onDrag = onDrag,
        onClick = onClick,
        onRemove = onRemove
    ) {
        Text(
            text = category,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}