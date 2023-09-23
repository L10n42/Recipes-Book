package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.core.presentation.common.FieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    enable: Boolean = true,
    background: Color = MaterialTheme.colorScheme.surface,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    onActionClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        singleLine = true,
        enabled = enable,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions {
            onActionClick()
        },
        placeholder = {
            Text(
                text = hint,
                style = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        shape = FieldDefaults.shape,
        colors = FieldDefaults.colors(background = background),
        modifier = modifier
    )
}