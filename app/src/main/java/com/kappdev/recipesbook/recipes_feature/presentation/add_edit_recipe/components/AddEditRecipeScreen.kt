package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.DefaultSnackbarHost
import com.kappdev.recipesbook.core.presentation.common.NavigationHandler
import com.kappdev.recipesbook.core.presentation.common.SnackbarHandler
import com.kappdev.recipesbook.core.presentation.common.components.ActionButton
import com.kappdev.recipesbook.core.presentation.common.components.DefaultTopBar
import com.kappdev.recipesbook.core.presentation.common.components.InputField
import com.kappdev.recipesbook.core.presentation.common.components.LoadingDialog
import com.kappdev.recipesbook.core.presentation.common.components.SelectorField
import com.kappdev.recipesbook.core.presentation.common.components.VerticalSpace
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.core.presentation.navigation.navigateWithValue
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.AddEditRecipeViewModel

@Composable
fun AddEditRecipeScreen(
    navController: NavHostController,
    initialIngredients: List<Ingredient>?,
    initialMethod: List<String>?,
    recipeId: String?,
    viewModel: AddEditRecipeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val recipeName = viewModel.recipeName.value
    val recipeDescription = viewModel.recipeDescription.value
    val method = viewModel.method.value
    val ingredients = viewModel.ingredients.value
    val images = viewModel.images
    val isLoading = viewModel.isLoading.value

    LoadingDialog(isVisible = isLoading)

    NavigationHandler(navController = navController, navigateRoute = viewModel.navigateRoute)

    SnackbarHandler(
        snackbarState = scaffoldState.snackbarHostState,
        snackbarMessage = viewModel.snackbarMessage,
        onDismiss = {
            viewModel.clearSnackbarMessage()
        },
        onAction = { dismiss ->
            viewModel.clearSnackbarMessage()
            dismiss()
        }
    )

    val pickPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.addImage(uri)
            }
        }
    )

    LaunchedEffect(Unit) {
        initialIngredients?.let { viewModel.setIngredients(initialIngredients) }
        initialMethod?.let { viewModel.setMethod(initialMethod) }
        if (recipeId != null && viewModel.recipeId == null) {
            viewModel.getRecipeById(recipeId) {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background,
        snackbarHost = { state ->
            DefaultSnackbarHost(state = state)
        },
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
                RecipeImages(images) {
                    pickPhotoLauncher.launch("image/*")
                }

                VerticalSpace(16.dp)

                InputField(
                    value = recipeName,
                    hint = stringResource(R.string.name),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = viewModel::setRecipeName
                )

                InputField(
                    value = recipeDescription,
                    hint = stringResource(R.string.description),
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = viewModel::setRecipeDescription
                )

                SelectorField(
                    title = stringResource(R.string.ingredients).plusAmount(ingredients.size),
                    checked = ingredients.isNotEmpty()
                ) {
                    navController.navigateWithValue(
                        route = Screen.Ingredients.route,
                        valueKey = NavConst.INGREDIENTS_KEY,
                        value = ingredients
                    )
                }

                SelectorField(
                    title = stringResource(R.string.method_steps).plusAmount(method.size),
                    checked = method.isNotEmpty()
                ) {
                    navController.navigateWithValue(
                        route = Screen.AddEditMethod.route,
                        valueKey = NavConst.METHOD_STEPS_KEY,
                        value = method
                    )
                }
            }

            ActionButton(
                title = stringResource(R.string.save),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
            ) {
                viewModel.insertRecipe()
            }
        }
    }
}

private fun String.plusAmount(amount: Int): String {
    return buildString {
        append(this@plusAmount)
        if (amount > 0) {
            append(" ($amount)")
        }
    }
}