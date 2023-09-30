package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeCategories(
    categories: List<String>,
    selectedCategory: Int,
    onSelect: (Int) -> Unit
) {
    val density = LocalDensity.current
    val listState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val itemWidths = remember { mutableStateListOf<Int>() }
    var rowWidth by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedCategory) {
        val halfRowWidth = rowWidth / 2
        val halfItemWidth = itemWidths.getOrElse(selectedCategory) { 0 } / 2
        val padding = with(density) { 8.dp.toPx().roundToInt() }
        val offset = halfRowWidth - halfItemWidth - padding
        listState.animateScrollToItem(selectedCategory, scrollOffset = -offset)
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { rowWidth = it.width },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        flingBehavior = snapBehavior
    ) {
        itemsIndexed(categories) { index, category ->
            CategoryTab(
                title = category,
                isSelected = (index == selectedCategory),
                position = index,
                onClick = onSelect,
                onSizeChanged = { itemSize ->
                    itemWidths.add(index, itemSize.width)
                }
            )
        }
    }
}

@Composable
private fun CategoryTab(
    title: String,
    isSelected: Boolean,
    position: Int,
    onClick: (Int) -> Unit,
    onSizeChanged: (IntSize) -> Unit
) {
    val transition = updateTransition(targetState = isSelected, label = "is selected transition")

    val backgroundColor by transition.animateColor(label = "background color") { selected ->
        when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.surface
        }
    }
    val textColor by transition.animateColor(label = "text color") { selected ->
        when {
            selected -> MaterialTheme.colorScheme.onSurface
            else -> MaterialTheme.colorScheme.onBackground
        }
    }

    Text(
        text = title,
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .background(backgroundColor, CircleShape)
            .padding(8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick(position) }
            ).onSizeChanged(onSizeChanged),
        fontSize = 16.sp,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        color = textColor
    )
}