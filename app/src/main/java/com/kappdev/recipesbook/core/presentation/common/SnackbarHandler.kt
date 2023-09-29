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
    hostState: SnackbarHostState,
    snackbarState: SnackbarState,
    actionLabel: String = stringResource(R.string.dismiss),
    onDismiss: () -> Unit = {},
    onAction: () -> Unit = {}
) {
    val message = snackbarState.message.collectAsState(initial = "")
    val dismiss = { hostState.hideSnackbar() }

    LaunchedEffect(message.value) {
        if (message.value.isNotBlank()) {
            val snackbarResult = hostState.showSnackbar(
                message = message.value,
                actionLabel = actionLabel
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    snackbarState.clear()
                    onDismiss()
                }
                SnackbarResult.ActionPerformed -> {
                    snackbarState.clear()
                    dismiss()
                    onAction()
                }
            }
        }
    }
}

private fun SnackbarHostState.hideSnackbar() = this.currentSnackbarData?.dismiss()