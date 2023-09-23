package com.kappdev.recipesbook.recipes_feature.presentation.ingredients

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient

class IngredientsScreenState(
    initialIngredient: List<Ingredient> = emptyList()
) {

    private var _ingredients = mutableStateListOf<Ingredient>()
    val ingredient: List<Ingredient> = _ingredients

    var dialogData = mutableStateOf<Ingredient?>(null)
        private set

    var isDialogVisible = mutableStateOf(false)
        private set

    init {
        _ingredients.addAll(initialIngredient)
    }

    fun showDialog(data: Ingredient?) {
        dialogData.value = data
        isDialogVisible.value = true
    }

    fun hideDialog() {
        dialogData.value = null
        isDialogVisible.value = false
    }

    fun updateIngredient(ingredient: Ingredient) {
        val index = _ingredients.indexOf(ingredient)
        if (index != -1) {
            _ingredients[index] = ingredient
        }
    }

    fun addIngredient(value: Ingredient) {
        _ingredients.add(value)
    }

    fun removeIngredient(value: Ingredient) {
        _ingredients.remove(value)
    }

}