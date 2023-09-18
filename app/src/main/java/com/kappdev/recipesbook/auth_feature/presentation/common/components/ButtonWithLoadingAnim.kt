package com.kappdev.recipesbook.auth_feature.presentation.common.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonWithLoadingAnim(
    title: String,
    isLoading: Boolean,
    enable: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    onClick: () -> Unit
) {
    val animatedWidth by animateDpAsState(
        targetValue = if (isLoading) 50.dp else 280.dp, label = ""
    )

    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = enable,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .height(50.dp)
            .width(animatedWidth)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(42.dp)
            )
        } else {
            Text(
                text = title,
                fontSize = 18.sp,
                maxLines = 1
            )
        }
    }
}