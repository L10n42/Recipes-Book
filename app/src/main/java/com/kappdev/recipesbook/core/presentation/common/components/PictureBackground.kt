package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.kappdev.recipesbook.R

@Composable
fun PictureBackground(
    picture: Painter,
    scrimAlpha: Float,
    scrimColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
) {
    Box {
        Image(
            painter = picture,
            contentDescription = stringResource(R.string.background_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    scrimColor.copy(alpha = scrimAlpha)
                )
        )

        content()
    }
}