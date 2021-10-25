package com.megganbz.domain.model.characters

import kotlinx.serialization.Serializable

@Serializable
data class Characters(
    val id: Int? = null,
    val name: String? = null,
    val thumbnail: Image? = null
)
