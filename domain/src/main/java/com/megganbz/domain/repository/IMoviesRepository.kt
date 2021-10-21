package com.megganbz.domain.repository

import com.megganbz.domain.model.Movie

interface IMoviesRepository {
    suspend fun getPopularMoviesList(): List<Movie>?
}