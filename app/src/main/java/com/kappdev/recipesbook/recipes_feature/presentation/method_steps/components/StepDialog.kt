package com.kappdev.recipesbook.recipes_feature.presentation.method_steps.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.AnimatedTransitionDialog
import com.kappdev.recipesbook.core.presentation.common.components.DefaultDialogPlatform
import com.kappdev.recipesbook.core.presentation.common.components.InputField
import com.kappdev.recipesbook.core.presentation.common.components.TextDialogButton
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient

@Composable
fun StepDialog(
    initialData: String?,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var description by remember { mutableStateOf("") }
    val canConfirm by remember {
        derivedStateOf { description.isNotBlank() }
    }

    LaunchedEffect(Unit) {
        description = initialData ?: ""
    }

    AnimatedTransitionDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(bottom = 64.dp)
    ) { dialogHelper ->
        DefaultDialogPlatform {
            Text(
                text = getDialogTitle(data = initialData),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            InputField(
                value = description,
                singleLine = false,
                hint = stringResource(R.string.description),
                modifier = Modifier.fillMaxWidth(),
                background = MaterialTheme.colorScheme.background,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                onActionClick = {
                    if (canConfirm) {
                        onConfirm(description)
                        dialogHelper.triggerAnimatedDismiss()
                    }
                },
                onValueChange = { description = it }
            )

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.Bottom
            ) {
                TextDialogButton(title = stringResource(R.string.cancel)) {
                    dialogHelper.triggerAnimatedDismiss()
                }

                TextDialogButton(
                    title = getButtonTitle(data = initialData),
                    enable = canConfirm
                ) {
                    onConfirm(description)
                    dialogHelper.triggerAnimatedDismiss()
                }
            }
        }
    }
}

@Composable
private fun getButtonTitle(data: String?): String {
    return if (data != null) {
        stringResource(R.string.update)
    } else {
        stringResource(R.string.create)
    }
}

@Composable
private fun getDialogTitle(data: String?): String {
    return if (data != null) {
        stringResource(R.string.edit_step)
    } else {
        stringResource(R.string.new_step)
    }
}