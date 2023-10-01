package com.kappdev.recipesbook.recipes_feature.presentation.interactive_method.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.DefaultTopBar
import com.kappdev.recipesbook.core.presentation.common.components.bottomEdgeShade
import com.kappdev.recipesbook.core.presentation.common.components.topEdgeShade
import com.kappdev.recipesbook.recipes_feature.presentation.interactive_method.InteractiveMethodState

@Composable
fun InteractiveMethodScreen(
    navController: NavHostController,
    initialSteps: List<String>
) {
    val listState = rememberLazyListState()
    val state = remember { InteractiveMethodState(initialSteps) }
    val currentStep = state.currentStep.intValue

    LaunchedEffect(currentStep) {
        val listHeight = listState.layoutInfo.viewportSize.height
        val offset = (listHeight / 4)
        listState.animateScrollToItem(currentStep, scrollOffset = -offset)
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            DefaultTopBar(
                title = stringResource(R.string.method),
                onBack = { navController.popBackStack() }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            InteractiveButtons(
                onBack = state::backStep,
                onNext = state::nextStep,
                onLast = state.isOnTheLast(),
                onDone = { navController.popBackStack() }
            )
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
            contentPadding = PaddingValues(top = 8.dp, bottom = 82.dp)
        ) {
            itemsIndexed(
                items = state.steps,
                key = { _, item -> item.hashCode() }
            ) { index, step ->
                InteractiveStepCard(
                    step = step,
                    isCurrent = (currentStep == index)
                )
            }
        }
    }
}