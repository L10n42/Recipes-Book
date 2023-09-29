package com.kappdev.recipesbook.categories_feature.domain.repository

import com.kappdev.recipesbook.categories_feature.domain.model.Category
import com.kappdev.recipesbook.core.domain.util.Result

interface CategoriesRepository {

    suspend fun getCategories(): Result<List<String>>

    suspend fun insertCategories(categories: List<String>): Result<Unit>

}