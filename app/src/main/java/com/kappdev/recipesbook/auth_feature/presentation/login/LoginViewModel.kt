package com.kappdev.recipesbook.auth_feature.presentation.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.auth_feature.domain.repository.AuthRepository
import com.kappdev.recipesbook.core.domain.ViewModelWithLoading
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModelWithLoading() {

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    private val _navigateRoute = MutableSharedFlow<String>()
    val navigateRoute = _navigateRoute.asSharedFlow()

    val snackbarState = SnackbarState(context)

    fun loginUser() {
        viewModelScope.launch(Dispatchers.IO) {
            suspendLoading {
                validFields { email, password ->
                    val result = authRepository.logIn(email, password)
                    when {
                        result is ResultState.Success -> navigateTo(Screen.Recipes)
                        result is ResultState.Failure -> snackbarState.show(result.exception.message ?: "")
                    }
                }
            }
        }
    }

    private suspend fun validFields(block: suspend (email: String, password: String) -> Unit) {
        when {
            email.value.isBlank() -> snackbarState.show(R.string.enter_email)
            password.value.isBlank() -> snackbarState.show(R.string.enter_password)
            else -> block(email.value, password.value)
        }
    }

    private suspend fun navigateTo(screen: Screen) {
        _navigateRoute.emit(screen.route)
    }

    fun setEmail(value: String) {
        email.value = value
    }

    fun setPassword(value: String) {
        password.value = value
    }
}