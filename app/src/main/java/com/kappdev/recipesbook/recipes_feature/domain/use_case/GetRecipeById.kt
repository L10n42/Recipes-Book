package com.kappdev.recipesbook.recipes_feature.domain.use_case

import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeById @Inject constructor(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(id: String): ResultState<Recipe> {
        return recipeRepository.getRecipe(id)
    }
}