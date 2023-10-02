package com.kappdev.recipesbook.core.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class PhotoPickerState(
    private val scope: CoroutineScope
) {

    private var _launchPickerFlow = MutableSharedFlow<Any?>()
    val launchPickerFlow = _launchPickerFlow.asSharedFlow()

    fun launch() {
        scope.launch {
            _launchPickerFlow.emit(Random.nextInt())
        }
    }
}

@Composable
fun rememberPhotoPickerState(
    scope: CoroutineScope = rememberCoroutineScope()
) = remember {
    PhotoPickerState(scope)
}