package com.kappdev.recipesbook.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kappdev.recipesbook.auth_feature.presentation.greeting.components.GreetingScreen
import com.kappdev.recipesbook.auth_feature.presentation.login.components.LoginScreen
import com.kappdev.recipesbook.auth_feature.presentation.sign_up.components.SignUpScreen
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
    }
}