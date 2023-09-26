package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.BackButton
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
    val recipe = viewModel.recipe.value

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
                SubcomposeAsyncImage(
                    model = recipe.images.firstOrNull(),
                    contentDescription = stringResource(R.string.recipe_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                Actions(
                    onBack = { navController.popBackStack() },
                    onEdit = {
                        navController.navigateWithValue(
                            route = Screen.AddEditRecipe.route,
                            valueKey = NavConst.RECIPE_ID_KEY,
                            value = recipe.id
                        )
                    },
                    onDelete = { /* TODO */ }
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
                    Tab.RECIPE_METHOD -> RecipeMethod(steps = recipe.method)
                }
            }
        }
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
        selectedTabIndex = selectedTabIndex
    ) {
        Tab.values().forEachIndexed { index, tab ->
            val isSelected = (selectedTabIndex == index)
            Tab(
                selected = isSelected,
                text = {
                    Text(
                        text = stringResource(tab.resId),
                        fontSize = 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = when {
                            isSelected -> MaterialTheme.colorScheme.onSurface
                            else -> MaterialTheme.colorScheme.onBackground
                        }
                    )
                },
                onClick = {
                    selectTab(index)
                }
            )
        }
    }
}