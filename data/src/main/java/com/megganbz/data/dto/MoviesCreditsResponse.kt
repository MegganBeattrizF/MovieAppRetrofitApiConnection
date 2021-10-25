package com.megganbz.data.dto

import com.megganbz.domain.model.movies.Cast
import com.megganbz.domain.model.movies.Crew
import kotlinx.serialization.Serializable

@Serializable
data class MoviesCreditsResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)
