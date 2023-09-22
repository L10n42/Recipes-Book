package com.kappdev.recipesbook.recipes_feature.presentation.ingredients

import androidx.compose.runtime.mutableStateListOf
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient

class IngredientsState(
    initialIngredient: List<Ingredient> = emptyList()
) {

    private var _ingredients = mutableStateListOf<Ingredient>()
    val ingredient: List<Ingredient> = _ingredients

    init {
        _ingredients.addAll(initialIngredient)
    }

    fun addIngredient(value: Ingredient) {
        _ingredients.add(value)
    }

    fun removeIngredient(value: Ingredient) {
        _ingredients.remove(value)
    }

}