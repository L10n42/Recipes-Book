package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTabRow(
    scrollState: ScrollState = rememberScrollState(),
    containerColor: Color = MaterialTheme.colorScheme.surface,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    containerShape: Shape = CircleShape,
    indicatorShape: Shape = CircleShape,
    paddingValues: PaddingValues = PaddingValues(4.dp),
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 250, easing = FastOutSlowInEasing),
    fixedSize: Boolean = true,
    selectedTabPosition: Int = 0,
    tabItem: @Composable () -> Unit
) {
    Surface(
        color = containerColor,
        shape = containerShape
    ) {
        SubcomposeLayout(
            modifier = Modifier
                .padding(paddingValues)
                .selectableGroup()
                .horizontalScroll(scrollState)
        ) { constraints ->
            val tabMeasurable: List<Placeable> = subcompose(SubComposeId.PRE_CALCULATE_ITEM, tabItem)
                .map { it.measure(constraints) }

            val itemsCount = tabMeasurable.size
            val maxItemWidth = tabMeasurable.maxOf { it.width }
            val maxItemHeight = tabMeasurable.maxOf { it.height }

            val tabPlaceables = subcompose(SubComposeId.ITEM, tabItem).map {
                val c = if (fixedSize) constraints.copy(
                    minWidth = maxItemWidth,
                    maxWidth = maxItemWidth,
                    minHeight = maxItemHeight
                ) else constraints
                it.measure(c)
            }

            val tabRowWidth = if (fixedSize) maxItemWidth * itemsCount else tabPlaceables.sumOf { it.width }
            val tabRowHeight = tabPlaceables.maxOf { it.height }

            val tabPositions = List(itemsCount) { index ->
                val leftX = if (fixedSize) maxItemWidth * index else tabPlaceables.take(index).sumOf { it.width }
                val itemWidth = tabPlaceables[index].width
                TabPosition(leftX.toDp(), itemWidth.toDp())
            }

            layout(tabRowWidth, tabRowHeight) {
                subcompose(SubComposeId.INDICATOR) {
                    Box(
                        modifier = Modifier
                            .tabIndicator(tabPositions[selectedTabPosition], animationSpec)
                            .fillMaxWidth()
                            .height(maxItemHeight.toDp())
                            .background(color = indicatorColor, shape = indicatorShape)
                    )
                }.forEach {
                    it.measure(Constraints.fixed(tabRowWidth, maxItemHeight)).placeRelative(0, 0)
                }

                tabPlaceables.forEachIndexed { index, placeable ->
                    val x = if (fixedSize) {
                        maxItemWidth * index
                    } else {
                        tabPlaceables.take(index).sumOf { it.width }
                    }
                    placeable.placeRelative(x, 0)
                }
            }
        }
    }
}

private fun Modifier.tabIndicator(
    tabPosition: TabPosition,
    animationSpec: AnimationSpec<Dp>
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = tabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabPosition.width,
        animationSpec = animationSpec,
        label = "current tab width"
    )
    val indicatorOffset by animateDpAsState(
        targetValue = tabPosition.left,
        animationSpec = animationSpec,
        label = "indicator offset"
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
        .fillMaxHeight()
}

@Composable
fun TabTitle(
    title: String,
    isSelected: Boolean,
    position: Int,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    containerShape: Shape = CircleShape,
    paddingValues: PaddingValues = PaddingValues(8.dp),
    onClick: (Int) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Text(
        text = title,
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .background(containerColor, containerShape)
            .padding(paddingValues)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick(position) }
            ),
        fontSize = 16.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = when {
            isSelected -> MaterialTheme.colorScheme.onSurface
            else -> MaterialTheme.colorScheme.onBackground
        }
    )
}

private data class TabPosition(
    val left: Dp, val width: Dp
)

private enum class SubComposeId {
    PRE_CALCULATE_ITEM,
    ITEM,
    INDICATOR
}