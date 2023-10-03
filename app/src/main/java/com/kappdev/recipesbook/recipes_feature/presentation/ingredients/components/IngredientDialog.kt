package com.kappdev.recipesbook.recipes_feature.presentation.ingredients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.MutableDialogState
import com.kappdev.recipesbook.core.presentation.common.components.AnimatedTransitionDialog
import com.kappdev.recipesbook.core.presentation.common.components.DefaultDialogPlatform
import com.kappdev.recipesbook.core.presentation.common.components.InputField
import com.kappdev.recipesbook.core.presentation.common.components.TextDialogButton
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient

@Composable
fun IngredientDialog(
    state: MutableDialogState<Ingredient?>,
    onConfirm: (Ingredient) -> Unit
) {
    if (state.isDialogVisible.value) {
        IngredientDialog(
            initialData = state.dialogData.value,
            onDismiss = state::hideDialog,
            onConfirm = onConfirm
        )
    }
}

@Composable
private fun IngredientDialog(
    initialData: Ingredient?,
    onDismiss: () -> Unit,
    onConfirm: (Ingredient) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var units by remember { mutableStateOf("") }
    val canConfirm by remember {
        derivedStateOf { name.isNotBlank() && amount.isNotBlank() }
    }

    LaunchedEffect(Unit) {
        name = initialData?.name ?: ""
        amount = initialData?.amount ?: ""
        units = initialData?.units ?: ""
    }

    AnimatedTransitionDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(bottom = 100.dp)
    ) { dialogHelper ->
        DefaultDialogPlatform {
            val focusManager = LocalFocusManager.current

            Text(
                text = getDialogTitle(data = initialData),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            InputField(
                value = name,
                hint = stringResource(R.string.name),
                modifier = Modifier.fillMaxWidth(),
                background = MaterialTheme.colorScheme.background,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                onActionClick = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                onValueChange = { name = it }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InputField(
                    value = amount,
                    modifier = Modifier.width(128.dp),
                    background = MaterialTheme.colorScheme.background,
                    hint = stringResource(R.string.amount),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    onActionClick = {
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    onValueChange = { amount = it }
                )
                InputField(
                    value = units,
                    modifier = Modifier.width(128.dp),
                    background = MaterialTheme.colorScheme.background,
                    hint = stringResource(R.string.units),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    onActionClick = {
                        if (canConfirm) {
                            onConfirm(Ingredient(name, amount, units))
                            dialogHelper.triggerAnimatedDismiss()
                        }
                    },
                    onValueChange = { units = it }
                )
            }

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
                    onConfirm(Ingredient(name, amount, units))
                    dialogHelper.triggerAnimatedDismiss()
                }
            }
        }
    }
}

@Composable
private fun getButtonTitle(data: Ingredient?): String {
    return if (data != null) {
        stringResource(R.string.update)
    } else {
        stringResource(R.string.create)
    }
}

@Composable
private fun getDialogTitle(data: Ingredient?): String {
    return if (data != null) {
        stringResource(R.string.edit_ingredient)
    } else {
        stringResource(R.string.new_ingredient)
    }
}