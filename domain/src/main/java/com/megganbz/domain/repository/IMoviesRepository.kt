package com.megganbz.domain.repository

import com.megganbz.domain.model.movies.Cast
import com.megganbz.domain.model.movies.Crew
import com.megganbz.domain.movies.Movie
import com.megganbz.domain.movies.MovieDetails

interface IMoviesRepository {
    suspend fun getPopularMoviesList(page: Int, language: String): List<Movie>?
    suspend fun getMovieDescription(movieId: Int, language: String): MovieDetails?
    suspend fun getMovieCast(movieId: Int, language: String): List<Cast>?
    suspend fun getMovieCrew(movieId: Int, language: String): List<Crew>?
}