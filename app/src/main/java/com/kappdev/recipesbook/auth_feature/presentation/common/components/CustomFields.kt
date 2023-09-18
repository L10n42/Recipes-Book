package com.kappdev.recipesbook.auth_feature.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.FieldDefaults

@Composable
fun UsernameField(
    username: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onActionClick: () -> Unit = {},
    onUsernameChange: (String) -> Unit
) {
    CustomField(
        value = username,
        enable = enable,
        modifier = modifier,
        icon = Icons.Outlined.Person,
        hint = stringResource(R.string.name),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = imeAction
        ),
        onActionClick = onActionClick,
        onValueChange = onUsernameChange
    )
}

@Composable
fun EmailField(
    email: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onActionClick: () -> Unit = {},
    onEmailChange: (String) -> Unit
) {
    CustomField(
        value = email,
        enable = enable,
        modifier = modifier,
        icon = Icons.Outlined.Email,
        hint = stringResource(R.string.email),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        onActionClick = onActionClick,
        onValueChange = onEmailChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomField(
    value: String,
    hint: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    keyboardOptions: KeyboardOptions,
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
        leadingIcon = {
            Icon(
                imageVector = icon,
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
        shape = FieldDefaults.shape,
        colors = FieldDefaults.colors(),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
}