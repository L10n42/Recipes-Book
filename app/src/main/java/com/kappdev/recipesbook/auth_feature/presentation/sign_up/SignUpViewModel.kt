package com.kappdev.recipesbook.auth_feature.presentation.sign_up

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.auth_feature.domain.model.UserData
import com.kappdev.recipesbook.auth_feature.domain.repository.AuthRepository
import com.kappdev.recipesbook.core.domain.ViewModelWithLoading
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.core.presentation.common.InfoDialogState
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModelWithLoading() {

    val infoDialogState = InfoDialogState(context)

    var username = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var confirmPassword = mutableStateOf("")
        private set

    var profileImageUri = mutableStateOf<Uri?>(null)
        private set

    val snackbarState = SnackbarState(context)

    fun signUpUser() {
        viewModelScope.launch(Dispatchers.IO) {
            suspendLoading {
                validUserData { userData ->
                    val result = authRepository.signUp(userData)
                    when {
                        result is ResultState.Success -> infoDialogState.show(
                            titleRes = R.string.registered_title,
                            messageRes = R.string.confirm_email_msg
                        )
                        result is ResultState.Failure -> snackbarState.show(result.exception.message ?: "")
                    }
                }
            }
        }
    }

    private suspend fun validUserData(block: suspend (userData: UserData) -> Unit) {
        when {
            username.value.isBlank() -> snackbarState.show(R.string.enter_name)
            email.value.isBlank() -> snackbarState.show(R.string.enter_email)
            password.value.isBlank() -> snackbarState.show(R.string.enter_password)
            confirmPassword.value.isBlank() -> snackbarState.show(R.string.confirm_password_warning)
            password.value != confirmPassword.value -> snackbarState.show(R.string.wrong_confirmation_password)
            else -> block(packUserData())
        }
    }

    private fun packUserData(): UserData {
        return UserData(
            name = username.value,
            email = email.value,
            password = password.value,
            profile = profileImageUri.value
        )
    }

    fun setUsername(value: String) {
        username.value = value
    }

    fun setEmail(value: String) {
        email.value = value
    }

    fun setPassword(value: String) {
        password.value = value
    }

    fun setConfirmPassword(value: String) {
        confirmPassword.value = value
    }

    fun setProfileImage(imageUri: Uri) {
        profileImageUri.value = imageUri
    }
}