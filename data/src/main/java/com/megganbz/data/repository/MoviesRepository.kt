package com.megganbz.data.repository

import com.megganbz.data.remote.RemoteApiService
import com.megganbz.domain.model.movies.Cast
import com.megganbz.domain.model.movies.Crew
import com.megganbz.domain.movies.Movie
import com.megganbz.domain.movies.MovieDetails
import com.megganbz.domain.repository.IMoviesRepository

class MoviesRepository(private val remoteApiService: RemoteApiService) : IMoviesRepository {
    private val apiKey = "3c5e3dd17b0d1aedb9eec27268a77e3d"
    override suspend fun getPopularMoviesList(page: Int, language: String): List<Movie>? {
        return try {
            remoteApiService.getPopularMoviesList(apiKey, language, page)?.results
        } catch (e: Exception) {
            Throwable(e)
            emptyList()
        }
    }

    override suspend fun getMovieDescription(movieId: Int, language: String): MovieDetails? {
        return try {
            remoteApiService.getMovieDescription(movieId, apiKey, language)
        } catch (e: Exception) {
            Throwable(e)
            return null
        }
    }

    override suspend fun getMovieCast(movieId: Int, language: String): List<Cast>? {
        return try {
            remoteApiService.getMovieCredits(movieId, apiKey, language).cast
        } catch (e: Exception) {
            Throwable(e)
            return null
        }
    }

    override suspend fun getMovieCrew(movieId: Int, language: String): List<Crew>? {
        return try {
            remoteApiService.getMovieCredits(movieId, apiKey, language).crew
        } catch (e: Exception) {
            Throwable(e)
            return null
        }
    }
}