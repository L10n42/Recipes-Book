package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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
import com.kappdev.recipesbook.core.presentation.common.components.ImagePicker
import com.kappdev.recipesbook.core.presentation.common.components.InputField
import com.kappdev.recipesbook.core.presentation.common.components.LoadingDialog
import com.kappdev.recipesbook.core.presentation.common.components.SelectorField
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.core.presentation.navigation.navigateWithValue
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.AddEditRecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddEditRecipeScreen(
    navController: NavHostController,
    initialIngredients: List<Ingredient>?,
    initialMethod: List<String>?,
    initialCategory: String?,
    recipeId: String?,
    viewModel: AddEditRecipeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val descriptionBIVR = remember { BringIntoViewRequester() }
    val recipeName = viewModel.recipeName.value
    val recipeDescription = viewModel.recipeDescription.value
    val recipeCategory = viewModel.recipeCategory.value
    val method = viewModel.method.value
    val ingredients = viewModel.ingredients.value
    val images = viewModel.images
    val isLoading = viewModel.isLoading.value
    var showSaveDialog by remember { mutableStateOf(false) }
    var showImagePicker by remember { mutableStateOf(false) }

    if (showSaveDialog) {
        SaveChangesDialog(
            onSave = { viewModel.insertRecipe() },
            onDismiss = { showSaveDialog = false },
            onDiscard = { navController.popBackStack() }
        )
    }

    LoadingDialog(isVisible = isLoading)

    NavigationHandler(navController = navController, navigateRoute = viewModel.navigateRoute)

    SnackbarHandler(
        hostState = scaffoldState.snackbarHostState,
        snackbarState = viewModel.snackbarState
    )

    if (showImagePicker) {
        ImagePicker(
            onResult = viewModel::addImage,
            onDismiss = { showImagePicker = false }
        )
    }

    LaunchedEffect(Unit) {
        initialIngredients?.let { viewModel.setIngredients(initialIngredients) }
        initialMethod?.let { viewModel.setMethod(initialMethod) }
        initialCategory?.let { viewModel.setCategory(initialCategory) }

        if (recipeId != null && viewModel.recipeId == null) {
            viewModel.getRecipeById(recipeId) {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ActionButton(
                title = stringResource(R.string.save),
                modifier = Modifier.navigationBarsPadding()
            ) {
                viewModel.insertRecipe()
            }
        },
        snackbarHost = { state ->
            DefaultSnackbarHost(
                state = state,
                statusBarPadding = false,
                navigationBarPadding = false
            )
        },
        topBar = {
            DefaultTopBar(
                title = when {
                    (recipeId != null) -> stringResource(R.string.edit_recipe_title)
                    else -> stringResource(R.string.new_recipe_title)
                },
                onBack = {
                    if (viewModel.dataIsEmpty()) {
                        navController.popBackStack()
                    } else {
                        showSaveDialog = true
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecipeImages(
                images = images,
                removeImage = viewModel::removeImage,
                addImage = { showImagePicker = true }
            )

            InputField(
                value = recipeName,
                singleLine = false,
                hint = stringResource(R.string.name),
                onValueChange = viewModel::setRecipeName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            InputField(
                value = recipeDescription,
                singleLine = false,
                hint = stringResource(R.string.description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .bringIntoViewRequester(descriptionBIVR)
                    .onFocusChanged {
                        if (it.isFocused) {
                            scope.launch { descriptionBIVR.bringIntoView() }
                        }
                    },
                onValueChange = viewModel::setRecipeDescription
            )

            SelectorField(
                title = stringResource(R.string.ingredients).plusAmount(ingredients.size),
                checked = ingredients.isNotEmpty(),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                navController.navigateWithValue(
                    route = Screen.Ingredients.route,
                    valueKey = NavConst.INGREDIENTS_KEY,
                    value = ingredients
                )
            }

            SelectorField(
                title = stringResource(R.string.method_steps).plusAmount(method.size),
                checked = method.isNotEmpty(),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                navController.navigateWithValue(
                    route = Screen.AddEditMethod.route,
                    valueKey = NavConst.METHOD_STEPS_KEY,
                    value = method
                )
            }

            SelectorField(
                title = stringResource(R.string.category).plusCategory(recipeCategory),
                checked = recipeCategory.isNotEmpty(),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                navController.navigate(Screen.SelectCategory.route)
            }
        }
    }
}

private fun String.plusCategory(name: String): String {
    return buildString {
        append(this@plusCategory)
        if (name.isNotEmpty()) {
            append(": $name")
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