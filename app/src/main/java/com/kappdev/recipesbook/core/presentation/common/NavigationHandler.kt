package com.kappdev.recipesbook.core.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun NavigationHandler(
    navController: NavHostController,
    navigateRoute: SharedFlow<String>
) {
    val route = navigateRoute.collectAsState(initial = "")
    LaunchedEffect(route.value) {
        if (route.value.isNotEmpty()) {
            navController.navigate(route.value)
        }
    }
}