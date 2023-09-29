package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard
import com.kappdev.recipesbook.recipes_feature.domain.use_case.Highlight
import com.kappdev.recipesbook.ui.theme.Vermilion
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    data: RecipeCard,
    highlightArg: String = "",
    onClick: () -> Unit
) {
    var descriptionLineCount by remember { mutableIntStateOf(0) }
    var isImageVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            RecipeImage(data.images) {
                isImageVisible = true
            }

            Shade(
                isVisible = isImageVisible,
                visibleLines = descriptionLineCount
            )

            RecipeInfo(
                name = data.name,
                description = data.description,
                isImageVisible = isImageVisible,
                highlightArg = highlightArg,
                onLineCountChange = { lineCount ->
                    descriptionLineCount = lineCount
                }
            )
        }
    }
}

@Composable
private fun RecipeInfo(
    name: String,
    description: String,
    isImageVisible: Boolean,
    highlightArg: String,
    onLineCountChange: (lineCount: Int) -> Unit
) {
    val clickInteractionSource = remember { MutableInteractionSource() }
    var expandedDescription by remember { mutableStateOf(false) }
    val textColor by animateColorAsState(
        targetValue = if (isImageVisible) Color.White else Color.Black,
        label = "text color"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = clickInteractionSource,
                indication = null
            ) {
                expandedDescription = !expandedDescription
            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        RecipeName(name, textColor, highlightArg)

        if (description.isNotBlank()) {
            RecipeDescription(
                color = textColor,
                description = description,
                highlightArg = highlightArg,
                expanded = expandedDescription,
                onLineCountChange = onLineCountChange
            )
        }
    }
}

@Composable
private fun RecipeDescription(
    description: String,
    expanded: Boolean,
    color: Color,
    highlightArg: String,
    onLineCountChange: (lineCount: Int) -> Unit
) {
    var text by remember { mutableStateOf(AnnotatedString("")) }

    val maxLines by animateIntAsState(
        targetValue = if (expanded) 4 else 1,
        label = "max description lines"
    )

    LaunchedEffect(description, highlightArg) {
        val highlight = Highlight(HighlightStyle)
        text = highlight(arg = highlightArg, into = description)
    }

    Text(
        text = text,
        fontSize = 14.sp,
        maxLines = maxLines,
        lineHeight = 16.sp,
        color = color,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            onLineCountChange(textLayoutResult.lineCount)
        }
    )
}

@Composable
private fun RecipeName(
    name: String,
    color: Color,
    highlightArg: String,
) {
    var text by remember { mutableStateOf(AnnotatedString("")) }

    LaunchedEffect(name, highlightArg) {
        val highlight = Highlight(HighlightStyle)
        text = highlight(arg = highlightArg, into = name)
    }

    Text(
        text = text,
        fontSize = 16.sp,
        maxLines = 1,
        color = color,
        fontWeight = FontWeight.Medium,
        overflow = TextOverflow.Ellipsis
    )
}

private val HighlightStyle = SpanStyle(color = Vermilion, fontWeight = FontWeight.SemiBold)

@Composable
private fun Shade(
    isVisible: Boolean,
    visibleLines: Int
) {

    val shadeSize by animateFloatAsState(
        targetValue =  when {
            (isVisible && visibleLines <= 1) -> 0.6f
            (isVisible && visibleLines > 1) -> 0.6f - (0.05f * visibleLines)
            else -> 1f
        },
        label = "shade size"
    )

    val shadeAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f, label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    shadeSize to Color.Transparent,
                    1f to Color.Black
                )
            )
            .alpha(shadeAlpha)
    )
}

@Composable
private fun RecipeImage(
    images: List<String>,
    onLoaded: () -> Unit
) {
    var currentImageIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(images) {
        while (this.isActive) {
            delay(IMAGE_SWITCH_DELAY)
            currentImageIndex = if (currentImageIndex < images.lastIndex) {
                currentImageIndex + 1
            } else 0
        }
    }

    AnimatedContent(
        targetState = currentImageIndex,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(IMAGE_SWITCH_DURATION)
            ) togetherWith fadeOut(
                animationSpec = tween(IMAGE_SWITCH_DURATION)
            )
        },
        label = "animated image switch"
    ) { index ->
        SubcomposeAsyncImage(
            model = images.getOrNull(index),
            contentDescription = stringResource(R.string.recipe_image),
            contentScale = ContentScale.Crop,
            onSuccess = {
                onLoaded()
            },
            loading = {
                EmptyRecipeImage()
            },
            error = {
                EmptyRecipeImage()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

private const val IMAGE_SWITCH_DURATION = 2_000
private const val IMAGE_SWITCH_DELAY = 6_000L

@Composable
private fun EmptyRecipeImage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.8f),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}