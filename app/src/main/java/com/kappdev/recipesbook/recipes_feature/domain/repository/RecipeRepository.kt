package com.kappdev.recipesbook.recipes_feature.domain.repository

import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard
import com.kappdev.recipesbook.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipe(id: String): Result<Recipe>

    suspend fun getRecipes(): Flow<List<RecipeCard>>

    suspend fun deleteRecipe(recipeId: String): Result<Unit>

    suspend fun insertRecipe(recipe: Recipe): Result<Unit>
}