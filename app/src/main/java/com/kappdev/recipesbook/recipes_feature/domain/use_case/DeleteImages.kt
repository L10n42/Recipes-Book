package com.kappdev.recipesbook.recipes_feature.domain.use_case

import com.kappdev.recipesbook.recipes_feature.domain.repository.StorageRepository
import javax.inject.Inject

class DeleteImages @Inject constructor(
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(images: List<String>) {
        images.forEach { imageUrl ->
            storageRepository.deleterImage(imageUrl)
        }
    }
}