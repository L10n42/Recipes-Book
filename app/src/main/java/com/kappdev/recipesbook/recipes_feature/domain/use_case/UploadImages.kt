package com.kappdev.recipesbook.recipes_feature.domain.use_case

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.recipesbook.core.data.common.Storage
import com.kappdev.recipesbook.core.domain.model.ImageSource
import com.kappdev.recipesbook.core.domain.util.ResultState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadImages @Inject constructor(
    private val storage: FirebaseStorage,
) {

    private val imagesFolder = storage.reference.child(Storage.Folder.RECIPE_IMAGES)

    suspend operator fun invoke(images: List<ImageSource>): ResultState<List<String>> {
        return try {
            val downloadUrls = mutableListOf<String>()

            images.forEach { imageSource ->
                when (imageSource) {
                    is ImageSource.Uri -> downloadUrls.add(imageSource.value.uploadImage())
                    is ImageSource.Url -> downloadUrls.add(imageSource.value)
                }
            }

            ResultState.Success(downloadUrls)
        } catch (e: Exception) {
            ResultState.Failure(e)
        }
    }

    private suspend fun Uri.uploadImage(): String {
        val image = imagesFolder.child(generateImageName())
        image.putFile(this).await()
        return image.downloadUrl.await().toString()
    }

    private fun generateImageName(): String = "image-${System.currentTimeMillis()}"
}