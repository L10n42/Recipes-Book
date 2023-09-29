package com.kappdev.recipesbook.categories_feature.domain.use_case

import com.kappdev.recipesbook.categories_feature.domain.repository.CategoriesRepository
import com.kappdev.recipesbook.core.domain.util.Result
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val repository: CategoriesRepository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return repository.getCategories()
    }
}