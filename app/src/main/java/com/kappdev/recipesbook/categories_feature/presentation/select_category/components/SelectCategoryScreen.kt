package com.kappdev.recipesbook.categories_feature.presentation.select_category.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.categories_feature.presentation.select_category.SelectCategoryViewModel
import com.kappdev.recipesbook.core.presentation.common.components.DefaultTopBar
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.goBackWithValue

@Composable
fun SelectCategoryScreen(
    navController: NavHostController,
    viewModel: SelectCategoryViewModel = hiltViewModel()
) {
    val categories = viewModel.categories.value

    LaunchedEffect(Unit) {
        viewModel.getData { navController.popBackStack() }
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            DefaultTopBar(
                title = stringResource(R.string.select_category),
                onBack = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = categories,
                key = { it.hashCode() }
            ) { category ->
                SelectCategoryCard(category) {
                    navController.goBackWithValue(NavConst.RECIPE_CATEGORY_KEY, category)
                }
            }
        }
    }
}