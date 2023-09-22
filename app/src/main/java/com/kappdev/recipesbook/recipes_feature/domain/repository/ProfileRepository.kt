package com.kappdev.recipesbook.recipes_feature.domain.repository

import android.net.Uri
import com.kappdev.recipesbook.core.domain.util.ResultState
import com.kappdev.recipesbook.recipes_feature.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getUser(): Flow<User>

    suspend fun updateName(name: String): ResultState<Unit>

    suspend fun updateProfileImage(imageUri: Uri): ResultState<Unit>

    fun logout()

}