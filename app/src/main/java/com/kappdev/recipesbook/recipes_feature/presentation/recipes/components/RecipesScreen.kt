package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun RecipesScreen(
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            RecipesTopBar(
                newRecipe = { /* TODO: Go to add new recipe */ },
                showDrawer = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerShape = CustomDrawerShape(width = 0.95f),
        drawerContent = {
            RecipesDrawer()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

/** values should be between 0f and 1f*/
private fun CustomDrawerShape(width: Float = 1f, height: Float = 1f) = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val pixelRadius = with(density) { 16.dp.toPx() }
        return Outline.Rounded(
            RoundRect(
                left = 0f,
                top = 0f,
                right = size.width * width,
                bottom = size.height * height,
                topRightCornerRadius = CornerRadius(pixelRadius, pixelRadius),
                bottomRightCornerRadius = CornerRadius(pixelRadius, pixelRadius)
            )
        )
    }
}