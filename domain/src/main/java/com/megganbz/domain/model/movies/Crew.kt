package com.megganbz.domain.model.movies

import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val name: String,
    val profile_path: String?,
    val job: String,
    val department: String
)
