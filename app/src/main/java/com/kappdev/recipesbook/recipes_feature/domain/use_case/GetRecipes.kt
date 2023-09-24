package com.kappdev.recipesbook.recipes_feature.domain.use_case

import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard
import com.kappdev.recipesbook.recipes_feature.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipes @Inject constructor(
    private val recipesRepository: RecipeRepository
) {

    suspend operator fun invoke(): Flow<List<RecipeCard>> {
        return recipesRepository.getRecipes()
    }
}