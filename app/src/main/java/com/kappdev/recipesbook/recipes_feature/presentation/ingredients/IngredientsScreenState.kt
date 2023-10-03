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

    private var clickedItemIndex = -1

    init {
        _ingredients.addAll(initialIngredient)
    }

    fun moveItem(from: Int, to: Int) {
        _ingredients.apply {
            add(to, removeAt(from))
        }
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
        if (clickedItemIndex != -1) {
            _ingredients[clickedItemIndex] = ingredient
        }
    }

    fun addIngredient(value: Ingredient) {
        _ingredients.add(value)
    }

    fun removeIngredient(value: Ingredient) {
        _ingredients.remove(value)
    }

    fun clickItem(index: Int) {
        clickedItemIndex = index
    }
}