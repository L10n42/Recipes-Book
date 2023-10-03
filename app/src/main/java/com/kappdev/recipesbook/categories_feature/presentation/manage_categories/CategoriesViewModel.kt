package com.kappdev.recipesbook.categories_feature.presentation.manage_categories

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.categories_feature.domain.use_case.GetCategories
import com.kappdev.recipesbook.categories_feature.domain.use_case.InsertCategories
import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.getMessageOrEmpty
import com.kappdev.recipesbook.core.presentation.common.SnackbarState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategories: GetCategories,
    private val insertCategories: InsertCategories,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _categories = mutableStateListOf<String>()
    val categories: List<String> = _categories

    var dialogData = mutableStateOf<String?>(null)
        private set

    var isDialogVisible = mutableStateOf(false)
        private set

    val snackbarState = SnackbarState(context)

    private var clickedItemIndex = -1


    fun getData(onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCategories()
            when (result) {
                is Result.Success -> setCategories(result.value)
                is Result.Failure -> withContext(Dispatchers.Main) { onFailure() }
            }
        }
    }

    fun saveData(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = insertCategories(categories)
            when (result) {
                is Result.Success -> withContext(Dispatchers.Main) { onSuccess() }
                is Result.Failure -> snackbarState.show(result.getMessageOrEmpty())
            }
        }
    }

    private fun setCategories(value: List<String>) {
        _categories.clear()
        _categories.addAll(value)
    }

    fun moveItem(from: Int, to: Int) {
        _categories.apply {
            add(to, removeAt(from))
        }
    }

    fun showDialog(data: String?) {
        dialogData.value = data
        isDialogVisible.value = true
    }

    fun hideDialog() {
        dialogData.value = null
        isDialogVisible.value = false
    }

    fun updateCategory(category: String) {
        if (clickedItemIndex != -1) {
            _categories[clickedItemIndex] = category.trim()
        }
    }

    fun addCategory(value: String) {
        _categories.add(value.trim())
    }

    fun removeCategory(value: String) {
        _categories.remove(value)
    }

    fun clickItem(index: Int) {
        clickedItemIndex = index
    }
}