package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun AnimatedScaleInTransition(
    visible: Boolean,
    modifier: Modifier = Modifier,
    animationTime: Long = DEFAULT_DIALOG_ANIMATION_DURATION,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(
            animationSpec = tween(animationTime.toInt())
        ) + scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(animationTime.toInt())
        ),
        exit = scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(animationTime.toInt())
        ) + fadeOut(
            animationSpec = tween(animationTime.toInt())
        ),
        content = content
    )
}

const val DEFAULT_DIALOG_ANIMATION_DURATION = 450L