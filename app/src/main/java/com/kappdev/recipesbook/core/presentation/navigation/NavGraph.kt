package com.kappdev.recipesbook.core.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import com.kappdev.recipesbook.recipes_feature.presentation.method_steps.components.AddEditMethodScreen
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
        composable(
            Screen.Greeting.route,
            enterTransition = {
                when (this.initialState.destination.route) {
                    Screen.Login.route, Screen.SignUp.route, Screen.Recipes.route -> slideInRight()
                    else -> null
                }
            },
            exitTransition = { slideOutLeft() },
            popEnterTransition = { slideInRight() },
            popExitTransition = { slideOutLeft() }
        ) {
            GreetingScreen(navController)
        }

        composable(
            Screen.Login.route,
            enterTransition = { slideInLeft() },
            exitTransition = { slideOutLeft() },
            popEnterTransition = { slideInRight() },
            popExitTransition = { slideOutRight() }
        ) {
            LoginScreen(navController)
        }

        composable(
            Screen.SignUp.route,
            enterTransition = { slideInLeft() },
            exitTransition = { slideOutLeft() },
            popEnterTransition = { slideInRight() },
            popExitTransition = { slideOutRight() }
        ) {
            SignUpScreen(navController)
        }

        composable(
            Screen.Recipes.route,
            enterTransition = {
                when (this.initialState.destination.route) {
                    Screen.RecipeDetail.route -> null
                    Screen.Login.route -> slideInLeft()
                    else -> slideInRight()
                }
            },
            exitTransition = {
                when (this.targetState.destination.route) {
                    Screen.RecipeDetail.route -> null
                    Screen.Greeting.route -> slideOutRight()
                    else -> slideOutLeft()
                }
            },
            popEnterTransition = {
                when (this.initialState.destination.route) {
                    Screen.RecipeDetail.route -> null
                    else -> slideInRight()
                }
            },
            popExitTransition = {
                when (this.targetState.destination.route) {
                    Screen.RecipeDetail.route -> null
                    else -> slideOutLeft()
                }
            }
        ) {
            RecipesScreen(navController)
        }

        composable(
            Screen.AddEditRecipe.route,
            enterTransition = { slideInLeft() },
            exitTransition = {
                when (this.targetState.destination.route) {
                    Screen.Ingredients.route, Screen.AddEditMethod.route -> slideOutLeft()
                    else -> slideOutRight()
                }
            },
            popEnterTransition = { slideInRight() },
            popExitTransition = { slideOutRight() }
        ) { stackEntry ->
            val ingredients = stackEntry.catchValue<List<Ingredient>>(NavConst.INGREDIENTS_KEY)
            val method = stackEntry.catchValue<List<String>>(NavConst.METHOD_STEPS_KEY)
            val recipeId = stackEntry.catchValue<String>(NavConst.RECIPE_ID_KEY)
            AddEditRecipeScreen(navController, ingredients, method, recipeId)
        }

        composable(
            Screen.Ingredients.route,
            enterTransition = { slideInLeft() },
            exitTransition = { slideOutRight() },
            popEnterTransition = { slideInRight() },
            popExitTransition = { slideOutRight() }
        ) { stackEntry ->
            val ingredients = stackEntry.catchValue<List<Ingredient>>(NavConst.INGREDIENTS_KEY) ?: emptyList()
            IngredientsScreen(navController, ingredients)
        }

        composable(
            Screen.AddEditMethod.route,
            enterTransition = { slideInLeft() },
            exitTransition = { slideOutRight() },
            popEnterTransition = { slideInRight() },
            popExitTransition = { slideOutRight() }
        ) { stackEntry ->
            val method = stackEntry.catchValue<List<String>>(NavConst.METHOD_STEPS_KEY) ?: emptyList()
            AddEditMethodScreen(navController, method)
        }

        composable(
            Screen.RecipeDetail.route,
            enterTransition = { popIn() },
            exitTransition = { popOut() },
            popEnterTransition = { popIn() },
            popExitTransition = { popOut() }
        ) { stackEntry ->
            val recipeId = stackEntry.catchValue<String>(NavConst.RECIPE_ID_KEY)
            if (recipeId != null) {
                RecipeDetailsScreen(navController, recipeId)
            } else {
                navController.popBackStack()
            }
        }
    }
}

private fun popIn(): EnterTransition {
    return fadeIn(
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    ) + scaleIn(
        initialScale = 0.5f,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
}

private fun popOut(): ExitTransition {
    return scaleOut(
        targetScale = 0.5f,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    ) + fadeOut(
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInLeft(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInRight(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutRight(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutLeft(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
        animationSpec = tween(DEFAULT_ANIM_DURATION)
    )
}

private const val DEFAULT_ANIM_DURATION = 700

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