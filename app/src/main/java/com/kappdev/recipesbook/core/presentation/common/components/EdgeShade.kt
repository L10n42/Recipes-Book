package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.kappdev.recipesbook.core.presentation.common.components.FadePosition.BOTTOM
import com.kappdev.recipesbook.core.presentation.common.components.FadePosition.LEFT
import com.kappdev.recipesbook.core.presentation.common.components.FadePosition.RIGHT
import com.kappdev.recipesbook.core.presentation.common.components.FadePosition.TOP

private enum class FadePosition {
    LEFT, RIGHT, BOTTOM, TOP
}

fun Modifier.topEdgeShade(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = TOP,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

fun Modifier.bottomEdgeShade(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = BOTTOM,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

fun Modifier.rightEdgeShade(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = RIGHT,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

fun Modifier.leftEdgeShade(
    color: Color,
    isVisible: Boolean = true,
    ratio: Float = DEFAULT_RATIO,
) = edgeShade(
    position = LEFT,
    color = color,
    ratio = ratio,
    isVisible = isVisible
)

private fun Modifier.edgeShade(
    position: FadePosition,
    color: Color,
    ratio: Float,
    isVisible: Boolean,
) = composed {

    val shadeAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        label = "shade alpha"
    )

    drawWithContent {
        this@drawWithContent.drawContent()

        val startOffset = this.size.getShadeStartOffset(position)
        val endOffset = this.size.getShadeEndOffset(position)

        drawRect(
            brush = Brush.linearGradient(
                0f to color,
                ratio to Color.Transparent,
                start = startOffset,
                end = endOffset
            ),
            alpha = shadeAlpha,
            topLeft = Offset.Zero,
            size = this.size
        )
    }
}

private fun Size.getShadeEndOffset(position: FadePosition): Offset {
    return when (position) {
        LEFT -> Offset(this.width, this.height / 2)
        RIGHT -> Offset(0f, this.height / 2)
        BOTTOM -> Offset(this.width / 2, 0f)
        TOP -> Offset(this.width / 2, this.height)
    }
}

private fun Size.getShadeStartOffset(position: FadePosition): Offset {
    return when (position) {
        LEFT -> Offset(0f, this.height / 2)
        RIGHT -> Offset(this.width, this.height / 2)
        BOTTOM -> Offset(this.width / 2, this.height)
        TOP -> Offset(this.width / 2, 0f)
    }
}

private const val DEFAULT_RATIO = 0.2f