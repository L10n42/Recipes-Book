package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    data: RecipeCard,
    onClick: () -> Unit
) {
    var isImageVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Box {
            RecipeImage(model = data.images.first()) {
                isImageVisible = true
            }

            Shade(isVisible = isImageVisible)

            RecipeName(
                name = data.name,
                isImageVisible = isImageVisible,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun RecipeName(
    name: String,
    isImageVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor by animateColorAsState(
        targetValue = if (isImageVisible) Color.White else Color.Black,
        label = ""
    )
    Text(
        text = name,
        fontSize = 16.sp,
        maxLines = 1,
        color = textColor,
        fontWeight = FontWeight.Medium,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
private fun Shade(
    isVisible: Boolean
) {
    val shadeSize by animateFloatAsState(
        targetValue = if (isVisible) 0.6f else 1f, label = ""
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
    model: Any?,
    onLoaded: () -> Unit
) {
    SubcomposeAsyncImage(
        model = model,
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