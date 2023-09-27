package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.AnimatedTransitionDialog
import com.kappdev.recipesbook.core.presentation.common.components.DefaultDialogPlatform
import com.kappdev.recipesbook.core.presentation.common.components.TextDialogButton

@Composable
fun SaveChangesDialog(
    onDismiss: () -> Unit,
    onDiscard: () -> Unit,
    onSave: () -> Unit
) {
    AnimatedTransitionDialog(
        onDismissRequest = onDismiss
    ) { dialogHelper ->
        DefaultDialogPlatform {
            Text(
                text = stringResource(R.string.save_changes_msg),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.Bottom
            ) {
                TextDialogButton(
                    title = stringResource(R.string.cancel)
                ) {
                    dialogHelper.triggerAnimatedDismiss()
                }

                TextDialogButton(
                    title = stringResource(R.string.discard)
                ) {
                    onDiscard()
                    dialogHelper.triggerAnimatedDismiss()
                }

                TextDialogButton(
                    title = stringResource(R.string.save)
                ) {
                    onSave()
                    dialogHelper.triggerAnimatedDismiss()
                }
            }
        }
    }
}