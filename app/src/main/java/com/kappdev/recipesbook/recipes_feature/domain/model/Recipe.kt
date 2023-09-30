package com.kappdev.recipesbook.recipes_feature.domain.model

data class Recipe(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val method: List<String> = emptyList(),
    val images: List<String> = emptyList(),
    val ingredients: List<Ingredient> = emptyList()
)
