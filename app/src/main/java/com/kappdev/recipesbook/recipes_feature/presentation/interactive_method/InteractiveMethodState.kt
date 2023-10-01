package com.kappdev.recipesbook.recipes_feature.presentation.interactive_method

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf

class InteractiveMethodState(
    initialSteps: List<String> = emptyList()
) {

    private var _steps = mutableStateListOf<String>()
    val steps: List<String> = _steps

    var currentStep = mutableIntStateOf(0)
        private set

    init {
        _steps.addAll(initialSteps)
    }

    fun isOnTheLast() = (currentStep.intValue == steps.lastIndex)

    fun nextStep() {
        currentStep.intValue += 1
    }

    fun backStep() {
        currentStep.intValue -= 1
    }
}