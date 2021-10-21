package com.megganbz.data.dto

import com.megganbz.domain.model.CharacterDataContainer
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDataWrapper(
        val data: CharacterDataContainer? = null
)