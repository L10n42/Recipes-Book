package com.kappdev.recipesbook.core.presentation.common

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class InfoDialogState(
    private val context: Context? = null
) {

    var message by mutableStateOf("")
        private set

    var title by mutableStateOf<String?>(null)
        private set

    var isVisible by mutableStateOf(false)
        private set

    fun show(message: String, title: String? = null) {
        this.message = message
        this.title = title
        isVisible = true
    }

    fun show(messageRes: Int, titleRes: Int? = null) {
        requireNotNull(context) { "If you want to use resources, the context have to be paste!" }
        this.message = context.getString(messageRes)
        this.title = titleRes?.let { context.getString(titleRes) }
        isVisible = true
    }

    fun hide() {
        isVisible = false
        clear()
    }

    private fun clear() {
        message = ""
        title = ""
    }
}

@Composable
fun rememberDialogState() = remember {
    InfoDialogState()
}
