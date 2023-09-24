package com.kappdev.recipesbook.core.presentation.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AnimatedComponentHelper(
    private val coroutineScope: CoroutineScope,
    private val onRemoveFlow: MutableSharedFlow<Any>
) {
    fun triggerAnimatedRemove() {
        coroutineScope.launch {
            onRemoveFlow.emit(Any())
        }
    }
}