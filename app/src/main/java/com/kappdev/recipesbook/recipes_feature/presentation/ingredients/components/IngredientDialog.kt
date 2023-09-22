package com.kappdev.recipesbook.recipes_feature.presentation.ingredients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.AnimatedTransitionDialog
import com.kappdev.recipesbook.core.presentation.common.components.InputField
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient

@Composable
fun IngredientDialog(
    initialData: Ingredient?,
    onDismiss: () -> Unit,
    onConfirm: (Ingredient) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var units by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        name = initialData?.name ?: ""
        amount = initialData?.amount ?: ""
        units = initialData?.units ?: ""
    }

    AnimatedTransitionDialog(
        onDismissRequest = onDismiss
    ) { dialogHelper ->
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val title = if (initialData != null) {
                    stringResource(R.string.edit_ingredient)
                } else {
                    stringResource(R.string.new_ingredient)
                }
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                InputField(
                    value = name,
                    hint = stringResource(R.string.name),
                    onValueChange = { name = it }
                )

                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InputField(
                        value = amount,
                        modifier = Modifier.width(128.dp),
                        hint = stringResource(R.string.amount),
                        onValueChange = { amount = it }
                    )
                    InputField(
                        value = units,
                        modifier = Modifier.width(128.dp),
                        hint = stringResource(R.string.units),
                        onValueChange = { units = it }
                    )
                }

                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(title = stringResource(R.string.cancel)) {
                        onDismiss()
                        dialogHelper.triggerAnimatedDismiss()
                    }

                    val buttonTitle = if (initialData != null) {
                        stringResource(R.string.update)
                    } else {
                        stringResource(R.string.create)
                    }
                    Button(
                        title = buttonTitle
                    ) {
                        onConfirm(Ingredient(name, amount, units))
                        dialogHelper.triggerAnimatedDismiss()
                    }
                }
            }
        }
    }
}

@Composable
private fun Button(
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