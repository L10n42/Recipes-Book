package com.kappdev.recipesbook.core.presentation.common

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.kappdev.recipesbook.R
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SnackbarHandler(
    snackbarState: SnackbarHostState,
    snackbarMessage: SharedFlow<String>,
    actionLabel: String = stringResource(R.string.dismiss),
    onDismiss: () -> Unit = {},
    onAction: (dismiss: () -> Unit) -> Unit = { snackbarState.hideSnackbar() }
) {
    val message = snackbarMessage.collectAsState(initial = "")
    val dismiss = {
        snackbarState.hideSnackbar()
    }
    LaunchedEffect(message.value) {
        if (message.value.isNotBlank()) {
            val snackbarResult = snackbarState.showSnackbar(
                message = message.value,
                actionLabel = actionLabel
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> onDismiss()
                SnackbarResult.ActionPerformed -> onAction(dismiss)
            }
        }
    }
}

private fun SnackbarHostState.hideSnackbar() = this.currentSnackbarData?.dismiss()