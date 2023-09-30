package com.kappdev.recipesbook.recipes_feature.presentation.recipes

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.categories_feature.domain.use_case.GetCategories
import com.kappdev.recipesbook.core.domain.ViewModelWithLoading
import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.recipes_feature.domain.model.RecipeCard
import com.kappdev.recipesbook.recipes_feature.domain.model.User
import com.kappdev.recipesbook.recipes_feature.domain.repository.ProfileRepository
import com.kappdev.recipesbook.recipes_feature.domain.use_case.GetRecipes
import com.kappdev.recipesbook.recipes_feature.domain.use_case.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val getRecipes: GetRecipes,
    private val getCategories: GetCategories,
    @ApplicationContext private val context: Context
) : ViewModelWithLoading() {
    private val categoryAll = context.getString(R.string.category_all_categories)
    private val search = Search()

    var searchArg = mutableStateOf("")
        private set

    var categories = mutableStateOf<List<String>>(emptyList())
        private set

    var selectedCategory = mutableIntStateOf(0)
        private set

    private var sourceRecipes: List<RecipeCard> = emptyList()
    private var searchResultRecipes: List<RecipeCard> = emptyList()
    var recipes = mutableStateOf<List<RecipeCard>>(emptyList())
        private set

    var user = mutableStateOf(User())
        private set

    private var searchJob: Job? = null
    private var filterJob: Job? = null

    private val _navigateRoute = MutableSharedFlow<String>()
    val navigateRoute = _navigateRoute.asSharedFlow()

    val snackbarState = SnackbarState(context)

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getUser().collectLatest { userData ->
                user.value = userData
            }
        }
    }

    fun getCategoriesData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCategories()
            when (result) {
                is Result.Failure -> TODO()
                is Result.Success -> categories.value = listOf(categoryAll) + result.value
            }
        }
    }

    fun getRecipesData() {
        viewModelScope.launch(Dispatchers.IO) {
            getRecipes().collectLatest { recipesList ->
                updateRecipesData(recipesList)
            }
        }
    }

    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            suspendLoading {
                val result = profileRepository.updateProfileImage(uri)
                when { 
                    result is ResultState.Success -> snackbarState.show(R.string.profile_photo_updated_msg)
                    result is ResultState.Failure -> snackbarState.show(R.string.failed_to_update_profile_photo_msg)
                }
            }
        }
    }

    private fun updateRecipesData(data: List<RecipeCard>) {
        sourceRecipes = data
        searchRecipe(searchArg.value)
        filterByCategory()
    }

    fun getRandomRecipe(): RecipeCard {
        return sourceRecipes.random()
    }

    fun addNewRecipe() {
        viewModelScope.launch {
            navigateTo(Screen.AddEditRecipe)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.logout()
            navigateTo(Screen.Greeting)
        }
    }

    fun searchRecipe(value: String) {
        searchArg.value = value
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(600)
            searchResultRecipes = search(searchArg.value, sourceRecipes)
            filterByCategory()
        }
    }

    fun filterByCategory() {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            val currentCategory = categories.value.getOrElse(selectedCategory.intValue) { "" }
            recipes.value = if (currentCategory.isEmpty() || currentCategory == categoryAll) {
                searchResultRecipes
            } else {
                searchResultRecipes.filter { card -> (card.category == currentCategory) }
            }
        }
    }

    fun selectCategory(index: Int) {
        selectedCategory.intValue = index
    }

    private suspend fun navigateTo(screen: Screen) {
        _navigateRoute.emit(screen.route)
    }
}