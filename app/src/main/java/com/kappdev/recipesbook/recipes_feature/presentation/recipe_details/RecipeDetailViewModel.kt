package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.core.domain.ViewModelWithLoading
import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.getMessageOrEmpty
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.use_case.DeleteRecipe
import com.kappdev.recipesbook.recipes_feature.domain.use_case.GetRecipeById
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipe: GetRecipeById,
    private val deleteRecipe: DeleteRecipe,
    @ApplicationContext private val context: Context
) : ViewModelWithLoading() {

    var recipe = mutableStateOf(Recipe())
        private set

    private val _navigateRoute = MutableSharedFlow<String>()
    val navigateRoute = _navigateRoute.asSharedFlow()

    private val snackbarState = SnackbarState(context)
    val snackbarMessage = snackbarState.message

    fun deleteCurrentRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            suspendLoading {
                val result = deleteRecipe(recipe.value)
                when (result) {
                    is Result.Success -> navigateTo(Screen.Recipes)
                    is Result.Failure -> snackbarState.show(result.getMessageOrEmpty())
                }
            }
        }
    }

    fun getRecipeById(id: String, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val recipeResult = getRecipe(id)
            when (recipeResult) {
                is Result.Success -> recipe.value = recipeResult.value
                is Result.Failure -> withContext(Dispatchers.Main) { onFailure() }
            }
        }
    }

    private suspend fun navigateTo(screen: Screen) {
        _navigateRoute.emit(screen.route)
    }

    fun clearSnackbarMessage() {
        viewModelScope.launch {
            snackbarState.clear()
        }
    }
}