package com.kappdev.recipesbook.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kappdev.recipesbook.auth_feature.presentation.greeting.components.GreetingScreen
import com.kappdev.recipesbook.auth_feature.presentation.login.components.LoginScreen
import com.kappdev.recipesbook.auth_feature.presentation.sign_up.components.SignUpScreen
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components.AddEditRecipeScreen
import com.kappdev.recipesbook.recipes_feature.presentation.ingredients.components.IngredientsScreen
import com.kappdev.recipesbook.recipes_feature.presentation.recipes.components.RecipesScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startScreen: Screen
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.route
    ) {
        composable(Screen.Greeting.route) {
            GreetingScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(Screen.Recipes.route) {
            RecipesScreen(navController)
        }

        composable(Screen.AddEditRecipe.route) { stackEntry ->
            val ingredients = stackEntry.catchValue<List<Ingredient>>(NavConst.INGREDIENTS_KEY) ?: emptyList()
            AddEditRecipeScreen(navController, ingredients)
        }

        composable(Screen.Ingredients.route) { stackEntry ->
            val ingredients = stackEntry.catchValue<List<Ingredient>>(NavConst.INGREDIENTS_KEY) ?: emptyList()
            IngredientsScreen(navController, ingredients)
        }
    }
}

fun <T> NavBackStackEntry.catchValue(key: String): T? {
    return this.savedStateHandle.get<T>(key)
}

fun <T> NavHostController.navigateWithValue(route: String, valueKey: String, value: T?) {
    this.navigate(route)
    this.currentBackStackEntry?.savedStateHandle?.set(valueKey, value)
}

fun <T> NavHostController.goBackWithValue(key: String, value: T?) {
    this.popBackStack()
    this.currentBackStackEntry?.savedStateHandle?.set(key, value)
}