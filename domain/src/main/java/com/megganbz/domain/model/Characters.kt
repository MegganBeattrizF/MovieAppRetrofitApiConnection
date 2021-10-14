package com.megganbz.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Characters(
    val name: String? = null,
    val thumbnail: Image? = null
)
