package com.kappdev.recipesbook.core.domain.model

sealed class ImageSource(val model: Any?) {
    data class Uri(val value: android.net.Uri): ImageSource(value)
    data class Url(val value: String): ImageSource(value)
}