package com.kappdev.recipesbook.auth_feature.presentation.login.components

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
import com.kappdev.recipesbook.auth_feature.presentation.login.LoginViewModel
import com.kappdev.recipesbook.core.presentation.common.DefaultSnackbarHost
import com.kappdev.recipesbook.core.presentation.common.NavigationHandler
import com.kappdev.recipesbook.core.presentation.common.SnackbarHandler
import com.kappdev.recipesbook.core.presentation.common.components.PictureBackground
import com.kappdev.recipesbook.core.presentation.common.components.VerticalSpace

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val email = viewModel.email.value
    val password = viewModel.password.value
    val isLoading = viewModel.isLoading.value

    NavigationHandler(navController = navController, navigateRoute = viewModel.navigateRoute)

    SnackbarHandler(
        hostState = scaffoldState.snackbarHostState,
        snackbarState = viewModel.snackbarState
    )

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            DefaultSnackbarHost(state = hostState, alignment = Alignment.TopCenter)
        }
    ) { paddingValues ->
        PictureBackground(
            picture = painterResource(R.drawable.log_in_background),
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
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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

                    VerticalSpace(space = 8.dp)

                    ButtonWithLoadingAnim(
                        title = stringResource(R.string.log_in),
                        isLoading = isLoading,
                        enable = !isLoading
                    ) {
                        viewModel.loginUser()
                    }
                }
            }
        }
    }
}