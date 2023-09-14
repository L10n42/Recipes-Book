package com.kappdev.recipesbook.core.presentation.navigation

sealed class Screen(val route: String) {
    data object Greeting: Screen("greeting_screen")
}
