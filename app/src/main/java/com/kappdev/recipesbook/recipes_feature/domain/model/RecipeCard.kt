package com.kappdev.recipesbook.recipes_feature.domain.model

data class RecipeCard(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val images: List<String> = emptyList()
)
