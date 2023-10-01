package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.DefaultSnackbarHost
import com.kappdev.recipesbook.core.presentation.common.NavigationHandler
import com.kappdev.recipesbook.core.presentation.common.SnackbarHandler
import com.kappdev.recipesbook.core.presentation.common.components.BackButton
import com.kappdev.recipesbook.core.presentation.common.components.LoadingDialog
import com.kappdev.recipesbook.core.presentation.common.components.PictureBackground
import com.kappdev.recipesbook.core.presentation.common.components.VerticalSpace
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.core.presentation.navigation.navigateWithValue
import com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.RecipeDetailViewModel

@Composable
fun RecipeDetailsScreen(
    navController: NavHostController,
    recipeId: String,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val snackbarState = SnackbarHostState()
    val recipe = viewModel.recipe.value
    val isLoading = viewModel.isLoading.value

    LoadingDialog(isVisible = isLoading)

    NavigationHandler(navController = navController, navigateRoute = viewModel.navigateRoute)

    SnackbarHandler(
        hostState = snackbarState,
        snackbarState = viewModel.snackbarState
    )

    LaunchedEffect(Unit) {
        viewModel.getRecipeById(recipeId) {
            navController.popBackStack()
        }
    }

    PictureBackground(
        picture = painterResource(R.drawable.details_background),
        scrimAlpha = 0.86f
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                PhotosViewer(photos = recipe.images)

                Actions(
                    onBack = { navController.popBackStack() },
                    onEdit = {
                        navController.navigateWithValue(
                            route = Screen.AddEditRecipe.route,
                            valueKey = NavConst.RECIPE_ID_KEY,
                            value = recipe.id
                        )
                    },
                    onDelete = { viewModel.deleteCurrentRecipe() }
                )
            }

            VerticalSpace(16.dp)

            Text(
                text = recipe.name,
                fontSize = 18.sp,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = recipe.description,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                maxLines = 4,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            VerticalSpace(16.dp)

            Tabs(
                selectedTabIndex = selectedTabIndex,
                selectTab = { selectedTabIndex = it }
            )

            AnimatedContent(
                targetState = selectedTabIndex,
                label = ""
            ) { index ->
                when (Tab.values()[index]) {
                    Tab.INGREDIENTS -> IngredientsList(ingredients = recipe.ingredients)
                    Tab.RECIPE_METHOD -> RecipeMethod(steps = recipe.method) {
                        navController.navigateWithValue(
                            route = Screen.InteractiveMethod.route,
                            valueKey = NavConst.METHOD_STEPS_KEY,
                            value = recipe.method
                        )
                    }
                }
            }
        }
        DefaultSnackbarHost(state = snackbarState)
    }
}

@Composable
private fun Actions(
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        BackButton(onClick = onBack)

        RecipeMoreMenu(
            onDelete = onDelete,
            onEdit = onEdit
        )
    }
}

private enum class Tab(@StringRes val resId: Int) {
    INGREDIENTS(R.string.ingredients), RECIPE_METHOD(R.string.recipe_method)
}

@Composable
private fun Tabs(
    selectedTabIndex: Int,
    selectTab: (index: Int) -> Unit
) {
    TabRow(
        containerColor = Color.Transparent,
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                CustomIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        }
    ) {
        Tab.values().forEachIndexed { index, tab ->
            val isSelected = (selectedTabIndex == index)
            val selectedTextStyle = TextStyle(
                brush = Brush.verticalGradient(
                    0.4f to MaterialTheme.colorScheme.onSurface,
                    1f to MaterialTheme.colorScheme.primary
                ),
                fontWeight = FontWeight.SemiBold
            )

            val defaultTextStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal
            )

            Tab(
                selected = isSelected,
                modifier = Modifier.clip(
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ),
                text = {
                    Text(
                        text = stringResource(tab.resId),
                        fontSize = 18.sp,
                        style = if (isSelected) selectedTextStyle else defaultTextStyle
                    )
                },
                onClick = {
                    selectTab(index)
                }
            )
        }
    }
}

@Composable
private fun CustomIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(3.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)
            )
    )
}