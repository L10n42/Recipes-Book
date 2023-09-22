package com.kappdev.recipesbook.core.presentation.navigation

sealed class Screen(val route: String) {
    data object Greeting: Screen("greeting_screen")
    data object Login: Screen("log_in_screen")
    data object SignUp: Screen("sign_up_screen")
    data object Recipes: Screen("recipes_screen")
    data object AddEditRecipe: Screen("add_edit_recipe_screen")
    data object Ingredients: Screen("ingredients_screen")
}
