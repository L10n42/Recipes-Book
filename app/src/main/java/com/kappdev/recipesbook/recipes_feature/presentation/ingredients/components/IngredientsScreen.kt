package com.kappdev.recipesbook.recipes_feature.presentation.ingredients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.goBackWithValue
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.presentation.ingredients.IngredientsState

@Composable
fun IngredientsScreen(
    navController: NavHostController,
    initialIngredients: List<Ingredient>
) {
    val screenState = remember { IngredientsState(initialIngredients) }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            DefaultTopBar(title = stringResource(R.string.recipe_ingredients)) {
                navController.goBackWithValue(NavConst.INGREDIENTS_KEY, screenState.ingredient)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ActionButton(
                icon = Icons.Rounded.Add,
                title = stringResource(R.string.new_title)
            ) {

            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(screenState.ingredient) { ingredient ->
                IngredientCard(
                    ingredient = ingredient,
                    onRemove = {
                        screenState.removeIngredient(ingredient)
                    },
                    onDrag = { /*TODO*/ }
                )
            }
        }
    }
}