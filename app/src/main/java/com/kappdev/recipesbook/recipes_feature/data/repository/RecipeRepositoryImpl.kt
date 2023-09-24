package com.kappdev.recipesbook.recipes_feature.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.recipesbook.core.data.common.Firestore
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard
import com.kappdev.recipesbook.recipes_feature.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : RecipeRepository {

    private val user = firestore.collection(Firestore.Collection.USERS).document(auth.currentUser!!.uid)
    private val userRecipes = user.collection(Firestore.Collection.RECIPES)

    override suspend fun getRecipe(id: String): ResultState<Recipe> {
        return try {
            val result = userRecipes.document(id).get().await()
            val recipe = result.toObject(Recipe::class.java)
            if (recipe != null) {
                ResultState.Success(recipe)
            } else {
                throw Exception("Couldn't get the recipe")
            }
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

    override suspend fun getRecipes(): Flow<List<RecipeCard>> {
        return userRecipes.snapshots().map {
            it.toObjects(RecipeCard::class.java)
        }
    }

    override suspend fun insertRecipe(recipe: Recipe): ResultState<Unit> {
        return try {
            userRecipes.document(recipe.id).set(recipe).await()
            ResultState.Success(Unit)
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

}