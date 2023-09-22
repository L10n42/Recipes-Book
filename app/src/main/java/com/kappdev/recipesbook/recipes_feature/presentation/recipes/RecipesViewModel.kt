package com.kappdev.recipesbook.recipes_feature.presentation.recipes

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.domain.ViewModelWithLoading
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.recipes_feature.domain.model.User
import com.kappdev.recipesbook.recipes_feature.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    @ApplicationContext private val context: Context
) : ViewModelWithLoading() {

    var searchArg = mutableStateOf("")
        private set

    var user = mutableStateOf(User())
        private set

    private val _navigateRoute = MutableSharedFlow<String>()
    val navigateRoute = _navigateRoute.asSharedFlow()

    private val snackbarState = SnackbarState(context)
    val snackbarMessage = snackbarState.message

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getUser().collectLatest { userData ->
                user.value = userData
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

    fun clearSnackbarMessage() {
        viewModelScope.launch {
            snackbarState.clear()
        }
    }

    fun search(value: String) {
        searchArg.value = value
    }

    private suspend fun navigateTo(screen: Screen) {
        _navigateRoute.emit(screen.route)
    }
}