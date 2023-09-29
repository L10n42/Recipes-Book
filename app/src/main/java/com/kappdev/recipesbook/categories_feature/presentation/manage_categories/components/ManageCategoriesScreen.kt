package com.kappdev.recipesbook.categories_feature.presentation.manage_categories.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.categories_feature.presentation.manage_categories.CategoriesViewModel
import com.kappdev.recipesbook.core.presentation.common.DefaultSnackbarHost
import com.kappdev.recipesbook.core.presentation.common.SnackbarHandler
import com.kappdev.recipesbook.core.presentation.common.components.ActionButton
import com.kappdev.recipesbook.core.presentation.common.components.DefaultTopBar
import com.kappdev.recipesbook.recipes_feature.presentation.method_steps.components.StepDialog

@Composable
fun ManageCategoriesScreen(
    navController: NavHostController,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val categories = viewModel.categories
    val dialogData = viewModel.dialogData.value
    val isDialogVisible = viewModel.isDialogVisible.value

    if (isDialogVisible) {
        StepDialog(
            initialData = dialogData,
            onDismiss = viewModel::hideDialog,
            onConfirm = { result ->
                if (dialogData != null) {
                    viewModel.updateCategory(result)
                } else {
                    viewModel.addCategory(result)
                }
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getData { navController.popBackStack() }
    }

    SnackbarHandler(
        hostState = scaffoldState.snackbarHostState,
        snackbarState = viewModel.snackbarState
    )

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        snackbarHost = { state ->
            DefaultSnackbarHost(
                state = state,
                statusBarPadding = false,
                navigationBarPadding = false
            )
        },
        topBar = {
            DefaultTopBar(title = stringResource(R.string.categories_title)) {
                viewModel.saveData { navController.popBackStack() }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ActionButton(
                icon = Icons.Rounded.Add,
                title = stringResource(R.string.new_title),
                modifier = Modifier.navigationBarsPadding()
            ) {
                viewModel.showDialog(data = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            itemsIndexed(
                items = categories,
                key = { _, item -> item.hashCode() }
            ) { index, category ->
                CategoryCard(
                    category = category,
                    onRemove = {
                        viewModel.removeCategory(category)
                    },
                    onClick = {
                        viewModel.clickItem(index)
                        viewModel.showDialog(category)
                    },
                    onDrag = { /*TODO*/ }
                )
            }
        }
    }
}