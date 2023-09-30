package com.kappdev.recipesbook.categories_feature.presentation.select_category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.recipesbook.categories_feature.domain.use_case.GetCategories
import com.kappdev.recipesbook.core.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectCategoryViewModel @Inject constructor(
    private val getCategories: GetCategories
) : ViewModel() {

    val categories = mutableStateOf<List<String>>(emptyList())

    fun getData(onFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCategories()
            when (result) {
                is Result.Success -> categories.value = result.value
                is Result.Failure -> withContext(Dispatchers.Main) { onFailure() }
            }
        }
    }
}