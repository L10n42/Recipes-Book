package com.kappdev.recipesbook.core.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

open class ViewModelWithLoading : ViewModel() {

    var isLoading = mutableStateOf(false)
        private set

    suspend fun suspendLoading(block: suspend () -> Unit) {
        isLoading.value = true
        block()
        isLoading.value = false
    }

    fun loading(block: () -> Unit) {
        isLoading.value = true
        block()
        isLoading.value = false
    }
}