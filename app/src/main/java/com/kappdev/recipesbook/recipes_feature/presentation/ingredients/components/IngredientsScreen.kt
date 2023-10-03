package com.kappdev.recipesbook.recipes_feature.presentation.ingredients.components

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
import com.kappdev.recipesbook.core.presentation.common.mutableDialogStateOf
import com.kappdev.recipesbook.core.presentation.common.rememberMutableDialogState
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.goBackWithValue
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.presentation.ingredients.IngredientsScreenState
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun IngredientsScreen(
    navController: NavHostController,
    initialIngredients: List<Ingredient>
) {
    val screenState = remember { IngredientsScreenState(initialIngredients) }
    val dialogState = rememberMutableDialogState<Ingredient?>(null)

    val state = rememberReorderableLazyListState(
        onMove = { from, to ->
            screenState.moveItem(from.index, to.index)
        }
    )

    IngredientDialog(
        state = dialogState,
        onConfirm = { result ->
            if (dialogState.dialogData.value != null) {
                screenState.updateIngredient(result)
            } else {
                screenState.addIngredient(result)
            }
        }
    )

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            DefaultTopBar(title = stringResource(R.string.recipe_ingredients)) {
                navController.goBackWithValue(NavConst.INGREDIENTS_KEY, screenState.ingredient.toList())
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ActionButton(
                icon = Icons.Rounded.Add,
                title = stringResource(R.string.new_title),
                modifier = Modifier.navigationBarsPadding()
            ) {
                dialogState.showDialog(data = null)
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
                    isVisible = state.listState.canScrollBackward,
                    ratio = 0.05f
                )
                .bottomEdgeShade(
                    MaterialTheme.colorScheme.background,
                    isVisible = state.listState.canScrollForward,
                    ratio = 0.05f
                )
                .reorderable(state),
            state = state.listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 82.dp)
        ) {
            itemsIndexed(
                items = screenState.ingredient,
                key = { _, item -> item.hashCode() }
            ) { index, ingredient ->
                ReorderableItem(reorderableState = state, key = ingredient.hashCode()) { isDragging ->
                    IngredientCard(
                        ingredient = ingredient,
                        reorderableState = state,
                        onRemove = {
                            screenState.removeIngredient(ingredient)
                        },
                        onClick = {
                            screenState.clickItem(index)
                            dialogState.showDialog(ingredient)
                        },
                    )
                }
            }
        }
    }
}