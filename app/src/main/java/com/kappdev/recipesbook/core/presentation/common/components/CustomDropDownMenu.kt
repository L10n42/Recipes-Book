package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun CustomDropDownMenu(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    elevation: Dp = 16.dp,
    offset: IntOffset = IntOffset(0, 0),
    alignment: Alignment = Alignment.TopEnd,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = RoundedCornerShape(16.dp),
    dismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val expandedState = remember { MutableTransitionState(false) }
    expandedState.targetState = expanded

    if (expandedState.currentState || expandedState.targetState || !expandedState.isIdle) {
        Popup(
            onDismissRequest = dismiss,
            alignment = alignment,
            offset = offset,
            properties = PopupProperties(focusable = true)
        ) {
            AnimatedVisibility(
                visibleState = expandedState,
                enter = fadeIn() + scaleIn(
                    initialScale = 0.3f,
                    transformOrigin = TransformOrigin(1f, 0f)
                ),
                exit = scaleOut(
                    targetScale = 0.3f,
                    transformOrigin = TransformOrigin(1f, 0f)
                ) + fadeOut()
            ) {
                Surface(
                    shape = shape,
                    color = backgroundColor,
                    elevation = elevation,
                    modifier = modifier,
                    content = { Column(content = content) }
                )
            }
        }
    }
}