package com.kappdev.recipesbook.recipes_feature.presentation.recipes.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.common.components.PictureBackground
import com.kappdev.recipesbook.core.presentation.common.components.ProfileImage
import com.kappdev.recipesbook.core.presentation.common.components.VerticalSpace
import com.kappdev.recipesbook.core.presentation.navigation.NavConst
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.core.presentation.navigation.navigateWithValue
import com.kappdev.recipesbook.recipes_feature.presentation.recipes.RecipesViewModel
import kotlinx.coroutines.launch

@Composable
fun RecipesDrawer(
    navController: NavHostController,
    viewModel: RecipesViewModel,
    closeDrawer: suspend () -> Unit
) {
    val user = viewModel.user.value
    val scope = rememberCoroutineScope()
    val pickPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.updateProfileImage(uri)
            }
        }
    )

    PictureBackground(
        picture = painterResource(R.drawable.drawer_background),
        scrimAlpha = 0.78f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProfileImage(
                model = user.profileImage,
                size = 100.dp
            ) {
                scope.launch {
                    closeDrawer()
                    pickPhotoLauncher.launch("image/*")
                }
            }

            Text(
                text = user.name,
                fontSize = 18.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = user.email,
                fontSize = 16.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

            VerticalSpace(8.dp)
            DrawerDivider()

            DrawerItem(icon = Icons.Rounded.Add, titleRes = R.string.new_recipe_title) {
                scope.launch {
                    closeDrawer()
                    viewModel.addNewRecipe()
                }
            }

            DrawerItem(icon = Icons.Rounded.Shuffle, titleRes = R.string.randrom_recipe_title) {
                scope.launch {
                    closeDrawer()
                    navController.navigateWithValue(
                        route = Screen.RecipeDetail.route,
                        valueKey = NavConst.RECIPE_ID_KEY,
                        value = viewModel.getRandomRecipe().id
                    )
                }
            }

            DrawerItem(icon = Icons.Rounded.Category, titleRes = R.string.categories_title) {
                scope.launch {
                    closeDrawer()
                    navController.navigate(Screen.ManageCategories.route)
                }
            }

            DrawerDivider()

            DrawerItem(icon = Icons.Rounded.Settings, titleRes = R.string.settings) {
                /* TODO: go to settings screen */
            }

            DrawerItem(icon = Icons.Rounded.Logout, titleRes = R.string.log_out_title) {
                viewModel.logout()
            }
        }
    }
}

@Composable
private fun DrawerDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 8.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.32f)
    )
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    @StringRes titleRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
        )

        Text(
            text = stringResource(titleRes),
            fontSize = 16.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis
        )
    }
}