package com.kappdev.recipesbook.core.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object FieldDefaults {

    val shape = RoundedCornerShape(8.dp)

    @Composable
    fun colors(
        background: Color = MaterialTheme.colorScheme.surface
    ) = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        disabledTextColor = MaterialTheme.colorScheme.onBackground,
        errorTextColor = MaterialTheme.colorScheme.error,

        focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground,
        errorPlaceholderColor = MaterialTheme.colorScheme.onBackground,

        errorIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,

        focusedContainerColor = background,
        unfocusedContainerColor = background,
        disabledContainerColor = background,
        errorContainerColor = background,

        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
        errorLeadingIconColor = MaterialTheme.colorScheme.onSurface,

        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        errorTrailingIconColor = MaterialTheme.colorScheme.onSurface
    )
}