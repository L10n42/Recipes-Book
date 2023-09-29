package com.kappdev.recipesbook.categories_feature.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.categories_feature.domain.repository.CategoriesRepository
import com.kappdev.recipesbook.core.data.common.Firestore
import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.fail
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : CategoriesRepository {

    private val user = firestore.collection(Firestore.Collection.USERS).document(auth.currentUser!!.uid)

    override suspend fun getCategories(): Result<List<String>> {
        return try {
            val result = user.get().await()
            if (!result.contains(Firestore.Field.USER_CATEGORIES)) {
                Result.Success(getDefaultCategories())
            } else {
                val categories = result.get(Firestore.Field.USER_CATEGORIES) as? List<String>
                if (categories != null) {
                    Result.Success(categories)
                } else {
                    Result.fail("Couldn't get categories")
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun insertCategories(categories: List<String>): Result<Unit> {
        return try {
            val data = mapOf(Firestore.Field.USER_CATEGORIES to categories)
            user.set(data)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    private fun getDefaultCategories(): List<String> {
        return context.resources.getStringArray(R.array.default_categories).toList()
    }
}