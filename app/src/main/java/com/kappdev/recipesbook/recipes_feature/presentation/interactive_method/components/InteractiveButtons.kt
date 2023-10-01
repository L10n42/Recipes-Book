package com.kappdev.recipesbook.recipes_feature.presentation.interactive_method.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.kappdev.recipesbook.core.presentation.common.components.HorizontalSpace

@Composable
fun InteractiveButtons(
    onBack: () -> Unit,
    onNext: () -> Unit,
    onDone: () -> Unit,
    onLast: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Button(
            onClick = onBack,
            shape = RoundedCornerShape(bottomStart = ButtonCornerShape, topStart = ButtonCornerShape),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = DefaultButtonsElevation,
                pressedElevation = PressedButtonsElevation
            ),
            modifier = Modifier
                .height(ButtonHeight)
                .width(ButtonWidth)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(IconSize),
                contentDescription = stringResource(R.string.back_interaction_button)
            )
            HorizontalSpace(16.dp)
            Text(
                text = stringResource(R.string.back),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        HorizontalSpace(8.dp)

        Button(
            onClick = {
                if (onLast) onDone() else onNext()
            },
            shape = RoundedCornerShape(bottomEnd = ButtonCornerShape, topEnd = ButtonCornerShape),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = DefaultButtonsElevation,
                pressedElevation = PressedButtonsElevation
            ),
            modifier = Modifier
                .height(ButtonHeight)
                .width(ButtonWidth)
        ) {
            Text(
                text = if (onLast) stringResource(R.string.done) else stringResource(R.string.next),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            HorizontalSpace(16.dp)
            Icon(
                imageVector = Icons.Rounded.ArrowForwardIos,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(IconSize),
                contentDescription = stringResource(R.string.next_interaction_button)
            )
        }
    }
}

private val DefaultButtonsElevation = 6.dp
private val PressedButtonsElevation = 12.dp

private val ButtonHeight = 48.dp
private val ButtonWidth = 132.dp

private val IconSize = 18.dp

private val ButtonCornerShape = 16.dp