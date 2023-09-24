package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.domain.ViewModelWithLoading
import com.kappdev.recipesbook.core.domain.util.GenerateId
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.repository.RecipeRepository
import com.kappdev.recipesbook.recipes_feature.domain.use_case.UploadImages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val uploadImages: UploadImages,
    @ApplicationContext private val context: Context
) : ViewModelWithLoading() {

    var recipeName = mutableStateOf("")
        private set

    var recipeDescription = mutableStateOf("")
        private set

    var ingredients = mutableStateOf<List<Ingredient>>(emptyList())
        private set

    var method = mutableStateOf<List<String>>(emptyList())
        private set

    private var _images = mutableStateListOf<Uri>()
    val images: List<Uri> = _images

    private val _navigateRoute = MutableSharedFlow<String>()
    val navigateRoute = _navigateRoute.asSharedFlow()

    private val snackbarState = SnackbarState(context)
    val snackbarMessage = snackbarState.message

    fun insertRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            validData {
                suspendLoading {
                    val downloadUrls = uploadImages(images)
                    if (downloadUrls is ResultState.Success) {
                        val recipe = packRecipe(downloadUrls.result)
                        val insertResult = recipeRepository.insertRecipe(recipe)
                        when {
                            (insertResult is ResultState.Success) -> navigateTo(Screen.Recipes)
                            (insertResult is ResultState.Failure) -> snackbarState.show(insertResult.exception.message ?: "")
                        }
                    } else if (downloadUrls is ResultState.Failure) {
                        snackbarState.show(downloadUrls.exception.message ?: "")
                    }
                }
            }
        }
    }

    private suspend fun validData(block: suspend () -> Unit) {
        when {
            images.isEmpty() -> snackbarState.show(R.string.empty_images_msg)
            recipeName.value.isBlank() -> snackbarState.show(R.string.enter_name)
            ingredients.value.isEmpty() -> snackbarState.show(R.string.empty_ingredients_msg)
            method.value.isEmpty() -> snackbarState.show(R.string.empty_method_msg)
            else -> block()
        }
    }

    private fun packRecipe(imageUrls: List<String>): Recipe {
        return Recipe(
            id = GenerateId.invoke(),
            name = recipeName.value.trim(),
            description = recipeDescription.value.trim(),
            method = method.value,
            images = imageUrls,
            ingredients = ingredients.value
        )
    }

    fun addImage(uri: Uri) {
        _images.add(uri)
    }

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

    private suspend fun navigateTo(screen: Screen) {
        _navigateRoute.emit(screen.route)
    }

    fun clearSnackbarMessage() {
        viewModelScope.launch {
            snackbarState.clear()
        }
    }
}