package com.kappdev.recipesbook.core.data.common

object Firestore {

    object Collection {
        const val USERS = "users"
        const val RECIPES = "recipes"
    }

    object Field {
        const val USER_UID = "uid"
        const val USER_NAME = "name"
        const val USER_EMAIL = "email"
        const val USER_PROFILE_IMAGE = "profile_image"
        const val USER_CATEGORIES = "user_categories"
    }

}