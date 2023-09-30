package com.kappdev.recipesbook.core.presentation.navigation

sealed class Screen(val route: String) {
    data object Greeting: Screen("greeting_screen")
    data object Login: Screen("log_in_screen")
    data object SignUp: Screen("sign_up_screen")

    data object Recipes: Screen("recipes_screen")
    data object AddEditRecipe: Screen("add_edit_recipe_screen")
    data object Ingredients: Screen("ingredients_screen")
    data object AddEditMethod: Screen("add_edit_method_screen")
    data object RecipeDetail: Screen("recipe_details_screen")

    data object ManageCategories: Screen("manage_categories_screen")
    data object SelectCategory: Screen("select_category_screen")
}
