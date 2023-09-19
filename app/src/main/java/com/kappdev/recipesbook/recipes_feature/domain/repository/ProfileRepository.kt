package com.kappdev.recipesbook.recipes_feature.domain.repository

import com.kappdev.recipesbook.recipes_feature.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getUser(): Flow<User>

}