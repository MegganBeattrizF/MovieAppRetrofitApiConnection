package com.megganbz.domain.usecases

import com.megganbz.domain.model.movies.Cast
import com.megganbz.domain.model.movies.Crew
import com.megganbz.domain.movies.Movie
import com.megganbz.domain.movies.MovieDetails
import com.megganbz.domain.repository.IMoviesRepository

class MoviesUseCases(private val repository: IMoviesRepository) {
    private val language = "pt-BR"

    suspend fun getPopularMoviesList(page: Int): List<Movie>? {
        return repository.getPopularMoviesList(page, language)
    }

    suspend fun getMovieDescription(movieId: Int): MovieDetails? {
        return repository.getMovieDescription(movieId, language)
    }

    suspend fun getMovieCast(movieId: Int): List<Cast>? {
        return repository.getMovieCast(movieId, language)
    }

    private suspend fun getMovieCrew(movieId: Int): List<Crew>? {
        return repository.getMovieCrew(movieId, language)
    }

    suspend fun getMovieDirector(movieId: Int): List<Crew>? {
        return getMovieCrew(movieId)?.filter {
            it.job.contains(JOB_DIRECTOR) && it.department.contains(DEPARTMENT_DIRECTING)
        }
    }

    companion object{
        private const val JOB_DIRECTOR = "Director"
        private const val DEPARTMENT_DIRECTING = "Directing"
    }
}