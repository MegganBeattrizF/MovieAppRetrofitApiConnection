package com.megganbz.domain.model.characters

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDataContainer(
    val results: List<Characters>? = null
)
