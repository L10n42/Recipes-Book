package com.kappdev.recipesbook.core.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

sealed interface DialogState<T> {
    val dialogData: State<T>
    val isDialogVisible: State<Boolean>
}

sealed interface MutableDialogState<T> : DialogState<T> {
    override val dialogData: State<T>
    override val isDialogVisible: State<Boolean>
    fun showDialog(data: T)
    fun showDialog()
    fun hideDialog()
}

private class MutableDialogStateHolder<T>(initialData: T) : MutableDialogState<T> {

    private var _dialogData = mutableStateOf(initialData)

    override val dialogData: State<T>
        get() = _dialogData

    private var _isDialogVisible = mutableStateOf(false)

    override val isDialogVisible: State<Boolean>
        get() = _isDialogVisible


    override fun showDialog() {
        _isDialogVisible.value = true
    }

    override fun showDialog(data: T) {
        _dialogData.value = data
        _isDialogVisible.value = true
    }

    override fun hideDialog() {
        _isDialogVisible.value = false
    }
}

fun <T> mutableDialogStateOf(initialData: T): MutableDialogState<T> {
    return MutableDialogStateHolder(initialData)
}

@Composable
fun <T> rememberMutableDialogState(initialData: T) = remember {
    mutableDialogStateOf(initialData)
}
