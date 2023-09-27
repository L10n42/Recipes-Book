package com.kappdev.recipesbook.core.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DefaultSnackbarHost(
    state: SnackbarHostState,
    statusBarPadding: Boolean = true,
    navigationBarPadding: Boolean = true,
    alignment: Alignment = Alignment.BottomCenter
) {
    val modifier = when {
        statusBarPadding && navigationBarPadding -> Modifier.statusBarsPadding().navigationBarsPadding()
        statusBarPadding -> Modifier.statusBarsPadding()
        navigationBarPadding -> Modifier.navigationBarsPadding()
        else -> Modifier
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {
        SnackbarHost(
            hostState = state,
            modifier = modifier
        ) { data ->
            Snackbar(snackbarData = data)
        }
    }
}