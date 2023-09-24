package com.kappdev.recipesbook.recipes_feature.domain.repository

import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipe(id: String): ResultState<Recipe>

    suspend fun getRecipes(): Flow<List<RecipeCard>>

    suspend fun insertRecipe(recipe: Recipe): ResultState<Unit>
}