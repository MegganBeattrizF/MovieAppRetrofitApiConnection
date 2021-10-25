package com.megganbz.domain.movies

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val title: String,
    val vote_count: Int,
    val vote_average: Double,
    val poster_path: String?,
    val overview: String,
    val runtime: Int,
    val release_date: String,
    val budget: Int,
    val revenue: Int
)
