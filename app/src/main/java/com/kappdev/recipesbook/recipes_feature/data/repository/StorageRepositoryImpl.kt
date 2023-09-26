package com.kappdev.recipesbook.recipes_feature.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.recipesbook.core.data.common.Storage
import com.kappdev.recipesbook.core.domain.model.ImageSource
import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.repository.StorageRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageRepository {

    private val imagesFolder = storage.reference.child(Storage.Folder.RECIPE_IMAGES)

    override suspend fun uploadImage(uri: Uri): Result<String> {
        return try {
            val image = imagesFolder.child(generateImageName())
            image.putFile(uri).await()
            val downloadUrl = image.downloadUrl.await()
            Result.Success(downloadUrl.toString())
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun deleterImage(url: String): Result<Unit> {
        return try {
            storage.getReferenceFromUrl(url).delete().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    private fun generateImageName(): String = "image-${System.currentTimeMillis()}"
}