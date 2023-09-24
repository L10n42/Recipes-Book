package com.kappdev.recipesbook.recipes_feature.presentation.method_steps

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class AddEditMethodScreenState(
    initialSteps: List<String> = emptyList()
) {

    private var _steps = mutableStateListOf<String>()
    val steps: List<String> = _steps

    var dialogData = mutableStateOf<String?>(null)
        private set

    var isDialogVisible = mutableStateOf(false)
        private set

    private var clickedItemIndex = -1

    init {
        _steps.addAll(initialSteps)
    }

    fun showDialog(data: String?) {
        dialogData.value = data
        isDialogVisible.value = true
    }

    fun hideDialog() {
        dialogData.value = null
        isDialogVisible.value = false
    }

    fun updateStep(step: String) {
        if (clickedItemIndex != -1) {
            _steps[clickedItemIndex] = step
        }
    }

    fun addStep(value: String) {
        _steps.add(value)
    }

    fun removeStep(value: String) {
        _steps.remove(value)
    }

    fun clickItem(index: Int) {
        clickedItemIndex = index
    }
}