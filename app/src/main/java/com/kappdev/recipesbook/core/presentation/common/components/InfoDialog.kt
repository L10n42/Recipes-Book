package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.InfoDialogState

@Composable
fun InfoDialog(
    state: InfoDialogState,
    isDismissible: Boolean = false,
    buttonText: String = stringResource(R.string.ok),
    onClick: () -> Unit
) {
    val title = state.title
    val message = state.message

    if (state.isVisible) {
        AnimatedTransitionDialog(
            dialogProperties = DialogProperties(
                dismissOnBackPress = isDismissible,
                dismissOnClickOutside = isDismissible
            ),
            onDismissRequest = state::hide
        ) { dialogHelper ->
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                ) {
                    title?.let {
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    val messageColor = when (title) {
                        null -> MaterialTheme.colorScheme.onSurface
                        else -> MaterialTheme.colorScheme.onBackground
                    }
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = messageColor
                    )

                    VerticalSpace(8.dp)

                    TextButton(
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        onClick = {
                            onClick()
                            dialogHelper.triggerAnimatedDismiss()
                        }
                    ) {
                        Text(
                            text = buttonText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}