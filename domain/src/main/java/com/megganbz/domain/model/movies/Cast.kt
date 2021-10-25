package com.megganbz.domain.model.movies

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val profile_path: String?
)