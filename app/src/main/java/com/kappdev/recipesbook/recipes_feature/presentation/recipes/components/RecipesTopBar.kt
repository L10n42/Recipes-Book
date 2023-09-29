package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.VerticalSpace
import com.kappdev.recipesbook.recipes_feature.presentation.recipes.RecipesViewModel

@Composable
fun RecipesTopBar(
    viewModel: RecipesViewModel,
    scrolled: Boolean,
    newRecipe: () -> Unit,
    showDrawer: () -> Unit
) {
    val searchValue = viewModel.searchArg.value
    val transition = updateTransition(targetState = scrolled, label = "scrolled state")

    val dividerAlpha by transition.animateFloat(label = "divider alpha") { isScrolled ->
        if (isScrolled) 1f else 0f
    }

    val animatedShadow by transition.animateDp(label = "shadow") { isScrolled ->
        if (isScrolled) 12.dp else 0.dp
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        elevation = animatedShadow
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier.statusBarsPadding(),
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = stringResource(R.string.your_recipes),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = showDrawer
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(R.string.recipes_menu)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = newRecipe
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(R.string.new_recipe)
                        )
                    }
                }
            )

            SearchBox(
                value = searchValue,
                openFilters = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 16.dp),
                onValueChange = viewModel::searchRecipe
            )

            VerticalSpace(16.dp)
            Divider(Modifier.alpha(dividerAlpha))
        }
    }
}