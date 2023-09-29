package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.ErrorImage
import com.kappdev.recipesbook.core.presentation.common.components.ImageLoadingAnimation
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosViewer(
    photos: List<String>
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { photos.size }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            key = { photos.getOrNull(it).hashCode() }
        ) { index ->
            SubcomposeAsyncImage(
                model = photos[index],
                contentDescription = stringResource(R.string.recipe_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = { ImageLoadingAnimation() },
                error = { ErrorImage() }
            )
        }

        NextSkipper(
            enable = pagerState.currentPage < photos.lastIndex,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }

        BackSkipper(
            enable = pagerState.currentPage > 0,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }

        if (photos.size > 1) {
            Indicator(
                itemsCount = photos.size,
                currentItem = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
private fun Indicator(
    itemsCount: Int,
    currentItem: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Color.Black.copy(0.42f),
                shape = CircleShape
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(itemsCount) { index ->
            Point(
                color = if (index == currentItem) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.primary.copy(0.64f)
                }
            )
        }
    }
}

@Composable
private fun Point(color: Color) {
    Canvas(
        modifier = Modifier.size(PointSize)
    ) {
        drawCircle(color = color)
    }
}

@Composable
private fun BackSkipper(
    enable: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(SkipperWidth)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enable,
                onClick = onClick
            )
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            drawArc(
                color = if (isPressed) Color.White.copy(0.42f) else Color.Transparent,
                startAngle = -90f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(-size.width, 0f),
                size = Size(size.width * 2, size.height)
            )
        }

        Icon(
            imageVector = Icons.Rounded.FastRewind,
            tint = if (isPressed) Color.White.copy(0.64f) else Color.Transparent,
            contentDescription = stringResource(R.string.right_image_button),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(54.dp)
                .padding(start = 16.dp)
        )
    }
}

@Composable
private fun NextSkipper(
    enable: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(SkipperWidth)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enable,
                onClick = onClick
            )
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            drawArc(
                color = if (isPressed) Color.White.copy(0.42f) else Color.Transparent,
                startAngle = -90f,
                sweepAngle = -180f,
                useCenter = true,
                size = Size(size.width * 2, size.height)
            )
        }

        Icon(
            imageVector = Icons.Rounded.FastForward,
            tint = if (isPressed) Color.White.copy(0.64f) else Color.Transparent,
            contentDescription = stringResource(R.string.right_image_button),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(54.dp)
                .padding(end = 16.dp)
        )
    }
}

private val SkipperWidth = 82.dp
private val PointSize = 8.dp