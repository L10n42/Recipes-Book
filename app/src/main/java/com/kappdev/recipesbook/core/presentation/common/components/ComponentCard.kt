package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DragIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.FieldDefaults
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatedComponentCard(
    onRemove: () -> Unit,
    onDrag: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }

    fun animatedRemove() {
        scope.launch {
            isVisible = false
            delay(CARD_ANIM_DURATION.toLong())
            onRemove()
        }
    }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(CARD_ANIM_DURATION)
        ) + scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(CARD_ANIM_DURATION)
        ),
        exit = scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(CARD_ANIM_DURATION)
        ) + fadeOut(
            animationSpec = tween(CARD_ANIM_DURATION)
        ),
    ) {
        DefaultComponentCard(
            modifier = modifier,
            onDrag = onDrag,
            onClick = onClick,
            content = content,
            onRemove = ::animatedRemove
        )
    }
}

@Composable
fun DefaultComponentCard(
    onRemove: () -> Unit,
    onDrag: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = FieldDefaults.shape
            )
            .clip(FieldDefaults.shape)
            .clickable(onClick = onClick)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalSpace(8.dp)
            Icon(
                imageVector = Icons.Rounded.DragIndicator,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.item_drag_indicator),
                modifier = Modifier.clickable(onClick = onDrag)
            )

            HorizontalSpace(4.dp)
            content()
        }

        HorizontalSpace(8.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardDivider()

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = stringResource(R.string.remove_item_button)
                )
            }
        }
    }
}

@Composable
private fun CardDivider(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = Modifier
            .width(1.dp)
            .fillMaxHeight(0.5f)
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            )
            .then(modifier)
    )
}

private const val CARD_ANIM_DURATION = 350