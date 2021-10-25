package com.megganbz.domain.movies

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val original_title: String,
    val vote_count: Int,
    val vote_average: Double,
    val poster_path: String?
)