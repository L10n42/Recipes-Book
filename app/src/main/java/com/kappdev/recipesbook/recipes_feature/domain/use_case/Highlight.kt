package com.kappdev.recipesbook.recipes_feature.domain.use_case

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

class Highlight(
    private val style: SpanStyle
) {

    operator fun invoke(arg: String, into: String): AnnotatedString {
        if (arg.isBlank() || into.isBlank()) {
            return AnnotatedString(into)
        }

        val annotatedString = AnnotatedString.Builder(into)

        val indexes = into.getAgrIndexes(arg)
        for (index in indexes) {
            if (index != -1) {
                annotatedString.addStyle(
                    style = style,
                    start = index,
                    end = index + arg.length
                )
            }
        }
        return annotatedString.toAnnotatedString()
    }

    private fun String.getAgrIndexes(arg: String): MutableList<Int> {
        val indexes = mutableListOf<Int>()
        var keywordIndex = this.indexOf(arg, ignoreCase = true)

        while (keywordIndex != -1) {
            indexes.add(keywordIndex)
            keywordIndex = this.indexOf(arg, keywordIndex + 1, ignoreCase = true)
        }

        return indexes
    }
}