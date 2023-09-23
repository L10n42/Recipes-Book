package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(

) : ViewModel() {

    var recipeName = mutableStateOf("")
        private set

    var recipeDescription = mutableStateOf("")
        private set

    var ingredients = mutableStateOf<List<Ingredient>>(emptyList())
        private set

    var method = mutableStateOf<List<String>>(emptyList())
        private set


    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients.value = ingredients
    }

    fun setMethod(method: List<String>) {
        this.method.value = method
    }

    fun setRecipeDescription(description: String) {
        recipeDescription.value = description
    }

    fun setRecipeName(name: String) {
        recipeName.value = name
    }
}