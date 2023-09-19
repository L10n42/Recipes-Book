package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.PictureBackground

@Composable
fun RecipesDrawer(

) {
    PictureBackground(
        picture = painterResource(R.drawable.drawer_background),
        scrimAlpha = 0.78f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {

        }
    }
}