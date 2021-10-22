package com.megganbz.data.dto

import com.megganbz.domain.model.characters.CharacterDataContainer
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDataWrapper(
        val data: CharacterDataContainer? = null
)