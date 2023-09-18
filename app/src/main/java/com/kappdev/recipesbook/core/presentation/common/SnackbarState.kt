package com.kappdev.recipesbook.core.presentation.common

import android.content.Context
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SnackbarState(
    private val context: Context
) {
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    suspend fun show(resId: Int) {
        val message = context.getString(resId)
        show(message)
    }

    suspend fun show(message: String) {
        _message.emit(message)
    }

    suspend fun clear() {
        _message.emit("")
    }
}