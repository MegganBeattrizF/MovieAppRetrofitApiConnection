package com.megganbz.data.dto

import com.megganbz.domain.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListPageResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
