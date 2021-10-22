package com.megganbz.domain.repository

import com.megganbz.domain.model.Movie

interface IMoviesRepository {
    suspend fun getPopularMoviesList(page: Int): List<Movie>?
}