package com.kappdev.recipesbook.recipes_feature.domain.use_case

import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.fail
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.repository.RecipeRepository
import javax.inject.Inject

class DeleteRecipe @Inject constructor(
    private val repository: RecipeRepository,
    private val deleteImages: DeleteImages
) {

    suspend operator fun invoke(recipe: Recipe): Result<Unit> {
        return if (recipe.id.isNotEmpty()) {
            deleteImages(recipe.images)
            repository.deleteRecipe(recipe.id)
        } else {
            Result.fail("Empty recipe id")
        }
    }
}