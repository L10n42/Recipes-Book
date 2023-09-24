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
import com.kappdev.recipesbook.recipes_feature.presentation.method_steps.components.AddEditMethodScreen
import com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe.components.AddEditRecipeScreen
import com.kappdev.recipesbook.recipes_feature.presentation.ingredients.components.IngredientsScreen
import com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components.RecipeDetailsScreen
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
            val method = stackEntry.catchValue<List<String>>(NavConst.METHOD_STEPS_KEY) ?: emptyList()
            AddEditRecipeScreen(navController, ingredients, method)
        }

        composable(Screen.Ingredients.route) { stackEntry ->
            val ingredients = stackEntry.catchValue<List<Ingredient>>(NavConst.INGREDIENTS_KEY) ?: emptyList()
            IngredientsScreen(navController, ingredients)
        }

        composable(Screen.AddEditMethod.route) { stackEntry ->
            val method = stackEntry.catchValue<List<String>>(NavConst.METHOD_STEPS_KEY) ?: emptyList()
            AddEditMethodScreen(navController, method)
        }

        composable(Screen.RecipeDetail.route) { stackEntry ->
            val recipeId = stackEntry.catchValue<String>(NavConst.RECIPE_ID_KEY)
            if (recipeId != null) {
                RecipeDetailsScreen(navController, recipeId)
            } else {
                navController.popBackStack()
            }
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