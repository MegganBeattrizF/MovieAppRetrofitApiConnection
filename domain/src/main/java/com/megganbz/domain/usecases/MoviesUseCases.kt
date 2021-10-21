package com.megganbz.domain.usecases

import com.megganbz.domain.model.Movie
import com.megganbz.domain.repository.IMoviesRepository

class MoviesUseCases(private val repository: IMoviesRepository) {
    suspend fun getPopularMoviesList(): List<Movie>?{
        return repository.getPopularMoviesList()
    }
}