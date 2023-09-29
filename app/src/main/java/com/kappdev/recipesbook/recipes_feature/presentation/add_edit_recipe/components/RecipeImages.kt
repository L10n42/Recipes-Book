package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.domain.model.ImageSource
import com.kappdev.recipesbook.core.presentation.common.components.ErrorImage
import com.kappdev.recipesbook.core.presentation.common.components.ImageLoadingAnimation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeImages(
    images: List<ImageSource>,
    addImage: () -> Unit,
    removeImage: (image: ImageSource) -> Unit,
) {
    val listState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    if (images.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Platform {
                EmptyImage(
                    icon = Icons.Rounded.AddPhotoAlternate,
                    modifier = Modifier.clickable(onClick = addImage)
                )
            }
        }
    } else {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Platform {
                    EmptyImage(
                        icon = Icons.Rounded.AddPhotoAlternate,
                        modifier = Modifier.clickable(onClick = addImage)
                    )
                }
            }
            items(images, { it.hashCode() }) { image ->
                Platform {
                    ImageCard(image = image) {
                        removeImage(image)
                    }
                }
            }
        }
    }
}

@Composable
private fun Platform(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(150.dp)
            .width(286.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp,
        color = MaterialTheme.colorScheme.surface,
        content = content
    )
}

@Composable
private fun ImageCard(
    image: ImageSource,
    onRemove: () -> Unit
) {
    Box {
        SubcomposeAsyncImage(
            model = image.model,
            contentDescription = stringResource(R.string.recipe_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = { ImageLoadingAnimation() },
            error = { ErrorImage() }
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(R.string.delete_image_icon)
            )
        }
    }
}

@Composable
private fun EmptyImage(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}