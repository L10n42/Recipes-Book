package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.core.presentation.common.DefaultSnackbarHost
import com.kappdev.recipesbook.core.presentation.common.NavigationHandler
import com.kappdev.recipesbook.core.presentation.common.SnackbarHandler
import com.kappdev.recipesbook.core.presentation.common.components.LoadingDialog
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard
import com.kappdev.recipesbook.recipes_feature.presentation.recipes.RecipesViewModel
import kotlinx.coroutines.launch

private val TestRecipesList = listOf(
    RecipeCard(name = "Salad", id = "fakf9jfs", images = listOf("https://www.allrecipes.com/thmb/k0Yugx575taH6eaSpD51xIC3s-4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AR-14452-GreenSalad-0025-4x3-527a1d42f2c042c9bcaf1a68223d34e5.jpg")),
    RecipeCard(name = "Borsch", id = "fdakhf", images = listOf("https://laplacinte.md/public/product_images/16/1016/3e44d56adc878028d4cf4999a9dd5e04.webp"))
)

@Composable
fun RecipesScreen(
    navController: NavHostController,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val user = viewModel.user.value
    val searchValue = viewModel.searchArg.value
    val isLoading = viewModel.isLoading.value

    NavigationHandler(navController = navController, navigateRoute = viewModel.navigateRoute)

    LoadingDialog(isVisible = isLoading)

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

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            RecipesTopBar(
                newRecipe = {
                    navController.navigate(Screen.AddEditRecipe.route)
                },
                showDrawer = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        snackbarHost = { state ->
            DefaultSnackbarHost(state = state)
        },
        drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
        drawerContent = {
            RecipesDrawer(user, viewModel) {
                scaffoldState.drawerState.close()
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchBox(
                value = searchValue,
                openFilters = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 16.dp),
                onValueChange = viewModel::search
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(TestRecipesList, { it.id }) { item ->
                    RecipeCard(data = item) {
                        /* TODO: open recipe info page */
                    }
                }
            }
        }
    }
}
