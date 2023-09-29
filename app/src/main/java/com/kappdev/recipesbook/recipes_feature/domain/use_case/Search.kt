package com.kappdev.recipesbook.recipes_feature.domain.use_case

import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard

class Search {

    operator fun invoke(argument: String, into: List<RecipeCard>): List<RecipeCard> {
        if (argument.isBlank()) {
            return into
        }

        return into.mapNotNull { recipe ->
            val searchArg = argument.lowercase()
            val recipeName = recipe.name.lowercase()
            val recipeDescription = recipe.description.lowercase()

            if (recipeName.contains(searchArg) || recipeDescription.contains(searchArg)) recipe else null
        }
    }
}