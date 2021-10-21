package com.megganbz.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
        val path: String? = null,
        val extension: String? = null
)
