package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextDialogButton(
    title: String,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    TextButton(
        enabled = enable,
        onClick = onClick
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}