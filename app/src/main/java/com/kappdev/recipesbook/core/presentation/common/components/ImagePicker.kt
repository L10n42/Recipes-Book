package com.kappdev.recipesbook.core.presentation.common.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.kappdev.recipesbook.core.presentation.common.PhotoPickerState

@Composable
fun ProfilePhotoPicker(
    state: PhotoPickerState,
    onError: (Exception) -> Unit = {},
    onResult: (Uri) -> Unit
) {
    val profilePhotoOptions = CropImageContractOptions(
        uri = null,
        cropImageOptions = CropImageOptions(
            fixAspectRatio = true,
            aspectRatioX = 1,
            aspectRatioY = 1
        )
    )

    PhotoPicker(
        state = state,
        options = profilePhotoOptions,
        onError = onError,
        onResult = onResult
    )
}

@Composable
fun RecipePhotoPicker(
    state: PhotoPickerState,
    onError: (Exception) -> Unit = {},
    onResult: (Uri) -> Unit
) {
    val recipePhotoOptions = CropImageContractOptions(
        uri = null,
        cropImageOptions = CropImageOptions(
            fixAspectRatio = true,
            aspectRatioX = 16,
            aspectRatioY = 9
        )
    )

    PhotoPicker(
        state = state,
        options = recipePhotoOptions,
        onError = onError,
        onResult = onResult
    )
}

@Composable
fun PhotoPicker(
    state: PhotoPickerState,
    options: CropImageContractOptions,
    onError: (Exception) -> Unit,
    onResult: (Uri) -> Unit
) {
    val launch by state.launchPickerFlow.collectAsState(initial = null)
    val pickerLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            val uriContent = result.uriContent
            if (result.isSuccessful && uriContent != null) {
                onResult(uriContent)
            } else {
                result.error?.let(onError)
            }
        }
    )

    LaunchedEffect(launch) {
        launch?.let { pickerLauncher.launch(options) }
    }
}