package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.use_case.GetRecipeById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipe: GetRecipeById
) : ViewModel() {

    var recipe = mutableStateOf(Recipe())
        private set


    fun getRecipeById(id: String, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val recipeResult = getRecipe(id)
            when {
                recipeResult is ResultState.Success -> recipe.value = recipeResult.result
                recipeResult is ResultState.Failure -> withContext(Dispatchers.Main) { onFailure() }
            }
        }
    }
}