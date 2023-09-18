package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable

@Composable
internal fun AnimatedScaleInTransition(
    visible: Boolean,
    animationTime: Long = DEFAULT_DIALOG_ANIMATION_DURATION,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(animationTime.toInt())
        ),
        exit = scaleOut(
            animationSpec = tween(animationTime.toInt())
        ),
        content = content
    )
}

const val DEFAULT_DIALOG_ANIMATION_DURATION = 450L