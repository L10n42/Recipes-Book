package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.core.presentation.common.DefaultSnackbarHost
import com.kappdev.recipesbook.core.presentation.common.NavigationHandler
import com.kappdev.recipesbook.core.presentation.common.SnackbarHandler
import com.kappdev.recipesbook.core.presentation.common.components.LoadingDialog
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.core.presentation.navigation.navigateWithValue
import com.kappdev.recipesbook.recipes_feature.presentation.recipes.RecipesViewModel
import kotlinx.coroutines.launch

@Composable
fun RecipesScreen(
    navController: NavHostController,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    val user = viewModel.user.value
    val isLoading = viewModel.isLoading.value
    val recipes = viewModel.recipes.value

    val scrolled by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset > 0 }
    }

    NavigationHandler(navController = navController, navigateRoute = viewModel.navigateRoute)

    LoadingDialog(isVisible = isLoading)

    LaunchedEffect(Unit) {
        viewModel.getUserData()
        viewModel.getRecipesData()
    }

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
                viewModel = viewModel,
                scrolled = scrolled,
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
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                items(recipes, { it.id }) { item ->
                    RecipeCard(data = item) {
                        navController.navigateWithValue(
                            route = Screen.RecipeDetail.route,
                            valueKey = NavConst.RECIPE_ID_KEY,
                            value = item.id
                        )
                    }
                }
            }
        }
    }
}
