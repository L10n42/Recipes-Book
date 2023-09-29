package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ImageLoadingAnimation(
    firstColor: Color = Color.LightGray,
    secondColor: Color = Color.Gray
) {
    val transition = rememberInfiniteTransition(label = "transition")
    val animatedColor = transition.animateColor(
        initialValue = firstColor,
        targetValue = secondColor,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1_000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "background color animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor.value)
    )
}