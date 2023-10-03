package com.kappdev.recipesbook.recipes_feature.presentation.method_steps.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.core.presentation.common.components.AnimatedComponentCard
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import org.burnoutcrew.reorderable.ReorderableState

@Composable
fun StepCard(
    step: String,
    onRemove: () -> Unit,
    reorderableState: ReorderableState<*>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    AnimatedComponentCard(
        modifier = modifier,
        reorderableState = reorderableState,
        onClick = onClick,
        onRemove = onRemove
    ) {
        Text(
            text = step,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}