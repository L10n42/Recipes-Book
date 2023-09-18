package com.kappdev.recipesbook.auth_feature.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.core.presentation.common.FieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    password: String,
    hint: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onPasswordChange: (String) -> Unit
) {
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    TextField(
        value = password,
        singleLine = true,
        enabled = enable,
        onValueChange = onPasswordChange,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation('*')
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Lock,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        placeholder = {
            Text(
                text = hint,
                style = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        trailingIcon = {
            VisibilitySwitcher(isVisible = isPasswordVisible) {
                isPasswordVisible = !isPasswordVisible
            }
        },
        shape = FieldDefaults.shape,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        colors = FieldDefaults.colors(),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
}

@Composable
private fun VisibilitySwitcher(
    isVisible: Boolean,
    onChange: () -> Unit
) {
    val icon = when (isVisible) {
        true -> Icons.Outlined.VisibilityOff
        false -> Icons.Outlined.Visibility
    }
    IconButton(
        onClick = onChange
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}