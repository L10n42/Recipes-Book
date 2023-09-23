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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun colors(
        background: Color = MaterialTheme.colorScheme.surface
    ) = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colorScheme.onSurface,
        disabledTextColor = MaterialTheme.colorScheme.onBackground,

        placeholderColor = MaterialTheme.colorScheme.onBackground,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground,

        errorIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,

        containerColor = background,

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