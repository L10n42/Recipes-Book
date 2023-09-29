package com.kappdev.recipesbook.auth_feature.presentation.sign_up.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.auth_feature.presentation.common.components.ButtonWithLoadingAnim
import com.kappdev.recipesbook.auth_feature.presentation.common.components.EmailField
import com.kappdev.recipesbook.auth_feature.presentation.common.components.PasswordField
import com.kappdev.recipesbook.auth_feature.presentation.common.components.UsernameField
import com.kappdev.recipesbook.auth_feature.presentation.sign_up.SignUpViewModel
import com.kappdev.recipesbook.core.presentation.common.DefaultSnackbarHost
import com.kappdev.recipesbook.core.presentation.common.SnackbarHandler
import com.kappdev.recipesbook.core.presentation.common.components.InfoDialog
import com.kappdev.recipesbook.core.presentation.common.components.PictureBackground
import com.kappdev.recipesbook.core.presentation.common.components.ProfileImage
import com.kappdev.recipesbook.core.presentation.common.components.VerticalSpace
import com.kappdev.recipesbook.core.presentation.navigation.Screen

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val username = viewModel.username.value
    val email = viewModel.email.value
    val password = viewModel.password.value
    val confirmPassword = viewModel.confirmPassword.value
    val profileImageUri = viewModel.profileImageUri.value
    val isLoading = viewModel.isLoading.value

    InfoDialog(state = viewModel.infoDialogState) {
        navController.navigate(Screen.Login.route)
    }

    SnackbarHandler(
        hostState = scaffoldState.snackbarHostState,
        snackbarState = viewModel.snackbarState
    )

    val pickProfileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.setProfileImage(uri)
            }
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { state ->
            DefaultSnackbarHost(state = state, alignment = Alignment.TopCenter)
        }
    ) { paddingValues ->
        PictureBackground(
            picture = painterResource(R.drawable.sign_up_background),
            scrimAlpha = 0.32f
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ProfileImage(
                        model = profileImageUri,
                        enable = !isLoading,
                    ) {
                        pickProfileLauncher.launch("image/*")
                    }

                    VerticalSpace(space = 8.dp)

                    UsernameField(
                        username = username,
                        enable = !isLoading,
                        onUsernameChange = viewModel::setUsername
                    )
                    EmailField(
                        email = email,
                        enable = !isLoading,
                        onEmailChange = viewModel::setEmail
                    )

                    PasswordField(
                        password = password,
                        enable = !isLoading,
                        hint = stringResource(R.string.password),
                        onPasswordChange = viewModel::setPassword
                    )
                    PasswordField(
                        password = confirmPassword,
                        enable = !isLoading,
                        hint = stringResource(R.string.confirm_password),
                        onPasswordChange = viewModel::setConfirmPassword
                    )

                    VerticalSpace(space = 8.dp)

                    ButtonWithLoadingAnim(
                        title = stringResource(R.string.sign_up),
                        isLoading = isLoading,
                        enable = !isLoading
                    ) {
                        viewModel.signUpUser()
                    }
                }
            }
        }
    }
}