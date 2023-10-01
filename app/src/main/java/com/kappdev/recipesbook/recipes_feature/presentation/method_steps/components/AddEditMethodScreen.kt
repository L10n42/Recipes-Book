package com.kappdev.recipesbook.recipes_feature.presentation.method_steps.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.ActionButton
import com.kappdev.recipesbook.core.presentation.common.components.DefaultTopBar
import com.kappdev.recipesbook.core.presentation.common.components.bottomEdgeShade
import com.kappdev.recipesbook.core.presentation.common.components.topEdgeShade
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.goBackWithValue
import com.kappdev.recipesbook.recipes_feature.presentation.method_steps.AddEditMethodScreenState

@Composable
fun AddEditMethodScreen(
    navController: NavHostController,
    initialSteps: List<String>
) {
    val listState = rememberLazyListState()
    val screenState = remember { AddEditMethodScreenState(initialSteps) }

    if (screenState.isDialogVisible.value) {
        StepDialog(
            initialData = screenState.dialogData.value,
            onDismiss = screenState::hideDialog,
            onConfirm = { result ->
                if (screenState.dialogData.value != null) {
                    screenState.updateStep(result)
                } else {
                    screenState.addStep(result)
                }
            }
        )
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            DefaultTopBar(title = stringResource(R.string.method_steps)) {
                navController.goBackWithValue(NavConst.METHOD_STEPS_KEY, screenState.steps.toList())
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ActionButton(
                icon = Icons.Rounded.Add,
                title = stringResource(R.string.new_title),
                modifier = Modifier.navigationBarsPadding()
            ) {
                screenState.showDialog(data = null)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .navigationBarsPadding()
                .topEdgeShade(
                    MaterialTheme.colorScheme.background,
                    isVisible = listState.canScrollBackward,
                    ratio = 0.05f
                )
                .bottomEdgeShade(
                    MaterialTheme.colorScheme.background,
                    isVisible = listState.canScrollForward,
                    ratio = 0.05f
                ),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 82.dp)
        ) {
            itemsIndexed(
                items = screenState.steps,
                key = { _, item -> item.hashCode() }
            ) { index, step ->
                StepCard(
                    step = step,
                    onRemove = {
                        screenState.removeStep(step)
                    },
                    onClick = {
                        screenState.clickItem(index)
                        screenState.showDialog(step)
                    },
                    onDrag = { /*TODO*/ }
                )
            }
        }
    }
}