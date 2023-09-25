package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components.BranchType.CLOSE
import com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components.BranchType.DEFAULT
import com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components.BranchType.OPEN
import com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components.BranchType.SINGLE

@Composable
fun RecipeMethod(
    steps: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.navigationBarsPadding(),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(
            items = steps,
            key = { _, item -> item.hashCode()  }
        ) { index, step ->
            Step(
                step = step,
                branchType = when {
                    (steps.size == 1) -> SINGLE
                    (index == 0) -> OPEN
                    (index == steps.lastIndex) -> CLOSE
                    else -> DEFAULT
                }
            )
        }
    }
}

@Composable
private fun Step(
    step: String,
    branchType: BranchType
) {
    val density = LocalDensity.current
    var boxHeightPx by remember { mutableIntStateOf(0) }
    val boxHeight by remember {
        derivedStateOf {
            with(density) { boxHeightPx.toDp() }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size ->
                boxHeightPx = size.height
            }
    ) {
        Branch(height = boxHeight, branchType = branchType)

        Text(
            text = step,
            fontSize = 14.sp,
            maxLines = 5,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f).padding(vertical = 8.dp)
        )
    }
}

private enum class BranchType {
    DEFAULT, OPEN, CLOSE, SINGLE
}

@Composable
private fun Branch(
    height: Dp,
    branchType: BranchType,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Canvas(
        modifier = Modifier
            .height(height)
            .width(16.dp)
    ) {
        val cWidth = this.size.width
        val cHeight = this.size.height

        val startOffset = when (branchType) {
            DEFAULT, CLOSE -> Offset(cWidth / 2, 0f)
            OPEN, SINGLE -> center
        }

        val endOffset = when (branchType) {
            DEFAULT, OPEN -> Offset(cWidth / 2, cHeight)
            CLOSE, SINGLE -> center
        }

        if (branchType != SINGLE) {
            drawLine(
                color = color,
                start = startOffset,
                end = endOffset,
                strokeWidth = 2.dp.toPx()
            )
        }

        drawCircle(
            color = color,
            radius = 4.dp.toPx()
        )
    }
}

