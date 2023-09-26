package com.kappdev.recipesbook.recipes_feature.domain.use_case

import com.kappdev.recipesbook.core.domain.model.ImageSource
import com.kappdev.recipesbook.core.domain.util.Result
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.repository.StorageRepository
import javax.inject.Inject

class UploadImages @Inject constructor(
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(images: List<ImageSource>): Result<List<String>> {
        return try {
            val downloadUrls = mutableListOf<String>()

            images.forEach { imageSource ->
                when (imageSource) {
                    is ImageSource.Uri -> {
                        val result = storageRepository.uploadImage(imageSource.value)
                        if (result is Result.Success) {
                            downloadUrls.add(result.value)
                        }
                    }
                    is ImageSource.Url -> downloadUrls.add(imageSource.value)
                }
            }

            Result.Success(downloadUrls)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}