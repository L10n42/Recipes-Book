package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.recipesbook.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PickImageCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .height(150.dp)
            .width(286.dp)
            .then(modifier),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.AddPhotoAlternate,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.pick_photo_icon),
                modifier = Modifier.size(100.dp)
            )
        }
    }
}