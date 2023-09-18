package com.kappdev.recipesbook.auth_feature.domain.model

import android.net.Uri

data class UserData(
    val name: String,
    val email: String,
    val password: String,
    val profile: Uri? = null
)
