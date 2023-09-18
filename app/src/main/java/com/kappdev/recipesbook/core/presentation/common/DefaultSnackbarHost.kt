package com.kappdev.recipesbook.core.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
    alignment: Alignment = Alignment.BottomCenter
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {
        SnackbarHost(
            hostState = state,
            modifier = Modifier
                .systemBarsPadding()
                .navigationBarsPadding()
        ) { data ->
            Snackbar(snackbarData = data)
        }
    }
}