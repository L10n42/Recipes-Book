package com.kappdev.recipesbook.core.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.recipesbook.R

@Composable
fun ProfileImage(
    model: Any?,
    modifier: Modifier = Modifier,
    size: Dp = 72.dp,
    enable: Boolean = true,
    onClick: () -> Unit = {},
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = stringResource(R.string.profile_image),
        contentScale = ContentScale.Crop,
        loading = {
            EmptyProfileImage()
        },
        error = {
            EmptyProfileImage()
        },
        modifier = Modifier
            .size(size)
            .then(modifier)
            .clip(CircleShape)
            .clickable(enabled = enable, onClick = onClick)
    )
}

@Composable
private fun EmptyProfileImage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(0.8f),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}