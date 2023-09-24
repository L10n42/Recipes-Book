package com.kappdev.recipesbook.recipes_feature.presentation.recipe_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.core.presentation.common.components.HorizontalSpace
import com.kappdev.recipesbook.recipes_feature.domain.model.Ingredient
import com.kappdev.recipesbook.recipes_feature.domain.model.buildAmountString

@Composable
fun IngredientsList(
    ingredients: List<Ingredient>
) {
    LazyColumn {
        items(ingredients) { ingredient ->
            Ingredient(
                ingredient = ingredient
            )
        }
    }
}

@Composable
private fun Ingredient(
    ingredient: Ingredient
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = ingredient.name,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        HorizontalSpace(4.dp)

        Text(
            text = ingredient.buildAmountString(),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )
    }
}