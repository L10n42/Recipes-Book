package com.kappdev.recipesbook.core.domain.util

import java.util.UUID

object GenerateId {

    operator fun invoke(): String {
        val uuid: UUID = UUID.randomUUID()
        return uuid.toString().replace("-", "")
    }
}