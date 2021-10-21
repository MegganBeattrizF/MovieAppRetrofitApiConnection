package com.megganbz.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDataContainer(
    val results: List<Characters>? = null
)
