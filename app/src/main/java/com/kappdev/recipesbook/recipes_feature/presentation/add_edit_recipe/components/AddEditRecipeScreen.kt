package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.ActionButton
import com.kappdev.recipesbook.core.presentation.common.components.DefaultTopBar
import com.kappdev.recipesbook.core.presentation.common.components.InputField
import com.kappdev.recipesbook.core.presentation.common.components.SelectorField
import com.kappdev.recipesbook.core.presentation.common.components.VerticalSpace
import com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.AddEditRecipeViewModel

@Composable
fun AddEditRecipeScreen(
    navController: NavHostController,
    viewModel: AddEditRecipeViewModel = hiltViewModel()
) {
    val recipeName = viewModel.recipeName.value
    val recipeDescription = viewModel.recipeDescription.value
    val ingredients = viewModel.ingredients.value
    val method = viewModel.method.value

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            DefaultTopBar(title = stringResource(R.string.new_recipe_title)) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PickImageCard {
                    /* TODO: go to pick images for that recipe */
                }

                VerticalSpace(16.dp)

                InputField(
                    value = recipeName,
                    hint = stringResource(R.string.name),
                    onValueChange = viewModel::setRecipeName
                )

                InputField(
                    value = recipeDescription,
                    hint = stringResource(R.string.description),
                    onValueChange = viewModel::setRecipeDescription
                )

                SelectorField(
                    title = stringResource(R.string.ingredients),
                    checked = ingredients.isNotEmpty()
                ) {
                    /* TODO: go to add ingredients */
                }

                SelectorField(
                    title = stringResource(R.string.method),
                    checked = method.isNotEmpty()
                ) {
                    /* TODO: go to add method steps */
                }
            }

            ActionButton(
                title = stringResource(R.string.save),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
            ) {
                /* TODO: save recipe */
            }
        }
    }
}