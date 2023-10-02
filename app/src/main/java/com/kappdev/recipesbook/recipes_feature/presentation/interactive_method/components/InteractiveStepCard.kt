package com.kappdev.recipesbook.recipes_feature.presentation.interactive_method.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.core.presentation.common.FieldDefaults

@Composable
fun InteractiveStepCard(
    step: String,
    isCurrent: Boolean
) {
    val transition = updateTransition(targetState = isCurrent, label = "is current card transition")

    val maxLines by transition.animateInt(
        label = "max lines", transitionSpec = { tween(ANIM_DURATION) }
    ) { selected ->
        if (selected) 10 else 1
    }

    val textColor by transition.animateColor(
        label = "text color", transitionSpec = { tween(ANIM_DURATION) }
    ) { selected ->
        if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onBackground
    }

    val shadowAlpha by transition.animateFloat(
        label = "shadow alpha", transitionSpec = { tween(ANIM_DURATION) }
    ) { selected ->
        if (selected) 0f else 0.32f
    }

    val elevation by transition.animateDp(
        label = "elevation", transitionSpec = { tween(ANIM_DURATION) }
    ) { selected ->
        if (selected) 8.dp else 0.dp
    }

    val padding by transition.animateDp(
        label = "padding", transitionSpec = { tween(ANIM_DURATION) }
    ) { selected ->
        if (selected) 8.dp else 16.dp
    }

    Surface(
        shape = FieldDefaults.shape,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = elevation,
        shadowElevation = elevation,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black.copy(alpha = shadowAlpha),
                    shape = FieldDefaults.shape
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = step,
                fontSize = 16.sp,
                color = textColor,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

private val ANIM_DURATION = 600