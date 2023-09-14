package com.kappdev.recipesbook.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kappdev.recipesbook.auth_feature.presentation.greeting.components.GreetingScreen

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
    }
}