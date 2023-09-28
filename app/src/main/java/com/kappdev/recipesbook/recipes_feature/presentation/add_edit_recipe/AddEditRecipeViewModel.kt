package com.kappdev.recipesbook.recipes_feature.presentation.add_edit_recipe

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.domain.ViewModelWithLoading
import com.kappdev.recipesbook.core.domain.model.ImageSource
import com.kappdev.recipesbook.core.domain.util.GenerateId
import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.getMessageOrEmpty
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.domain.model.Recipe
import com.kappdev.recipesbook.recipes_feature.domain.repository.RecipeRepository
import com.kappdev.recipesbook.recipes_feature.domain.use_case.GetRecipeById
import com.kappdev.recipesbook.recipes_feature.domain.use_case.UploadImages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    private val getRecipe: GetRecipeById,
    private val recipeRepository: RecipeRepository,
    private val uploadImages: UploadImages,
    @ApplicationContext private val context: Context
) : ViewModelWithLoading() {

    var recipeId: String? = null
        private set

    var recipeName = mutableStateOf("")
        private set

    var recipeDescription = mutableStateOf("")
        private set

    var ingredients = mutableStateOf<List<Ingredient>>(emptyList())
        private set

    var method = mutableStateOf<List<String>>(emptyList())
        private set

    private var _images = mutableStateListOf<ImageSource>()
    val images: List<ImageSource> = _images

    private val _navigateRoute = MutableSharedFlow<String>()
    val navigateRoute = _navigateRoute.asSharedFlow()

    private val snackbarState = SnackbarState(context)
    val snackbarMessage = snackbarState.message

    fun insertRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            validData {
                suspendLoading {
                    val result = uploadImages(images)
                    when (result) {
                        is Result.Success -> {
                            val recipe = packRecipe(result.value)
                            val insertResult = recipeRepository.insertRecipe(recipe)
                            when (insertResult) {
                                is Result.Success -> navigateTo(Screen.Recipes)
                                is Result.Failure -> snackbarState.show(insertResult.getMessageOrEmpty())
                            }
                        }
                        is Result.Failure -> snackbarState.show(result.getMessageOrEmpty())
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
            id = recipeId ?: GenerateId(),
            name = recipeName.value.trim(),
            description = recipeDescription.value.trim(),
            method = method.value,
            images = imageUrls,
            ingredients = ingredients.value
        )
    }

    fun getRecipeById(id: String, onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val recipeResult = getRecipe(id)
            when (recipeResult) {
                is Result.Success -> unpackRecipe(recipeResult.value)
                is Result.Failure -> withContext(Dispatchers.Main) { onFailure() }
            }
        }
    }

    private fun unpackRecipe(recipe: Recipe) {
        recipeId = recipe.id
        recipeName.value = recipe.name
        recipeDescription.value = recipe.description
        method.value = recipe.method
        ingredients.value = recipe.ingredients
        _images.addAll(
            recipe.images.map { ImageSource.Url(it) }
        )
    }

    fun dataIsEmpty(): Boolean {
        return recipeName.value.isEmpty() && recipeDescription.value.isEmpty() &&
                method.value.isEmpty() && ingredients.value.isEmpty() && images.isEmpty()
    }

    fun removeImage(image: ImageSource) {
        _images.remove(image)
    }

    fun addImage(uri: Uri) {
        _images.add(ImageSource.Uri(uri))
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