package com.kappdev.recipesbook.auth_feature.domain.repository

import com.kappdev.recipesbook.auth_feature.domain.model.UserData
import com.kappdev.recipesbook.core.domain.util.ResultState

interface AuthRepository {

    suspend fun signUp(user: UserData): ResultState<Unit>

    suspend fun logIn(email: String, password: String): ResultState<Unit>

}