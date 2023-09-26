package com.kappdev.recipesbook.recipes_feature.domain.repository

import android.net.Uri
import com.kappdev.recipesbook.core.domain.util.Result

interface StorageRepository {

    suspend fun uploadImage(uri: Uri): Result<String>

    suspend fun deleterImage(url: String): Result<Unit>

}