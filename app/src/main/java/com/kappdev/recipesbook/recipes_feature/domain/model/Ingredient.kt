package com.kappdev.recipesbook.recipes_feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val name: String = "",
    val amount: String = "",
    val units: String = ""
): Parcelable