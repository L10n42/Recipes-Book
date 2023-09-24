package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Image
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

@Composable
fun RecipeImages(
    images: List<Uri>,
    modifier: Modifier = Modifier,
    addImage: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(150.dp)
            .width(286.dp)
            .then(modifier),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        if (images.isNotEmpty()) {
            SubcomposeAsyncImage(
                model = images.first(),
                contentDescription = stringResource(R.string.recipe_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                error = {
                    EmptyImage(icon = Icons.Rounded.Image)
                },
                loading = {
                    EmptyImage(icon = Icons.Rounded.Image)
                }
            )
        } else {
            EmptyImage(
                icon = Icons.Rounded.AddPhotoAlternate,
                modifier = Modifier.clickable(onClick = addImage)
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