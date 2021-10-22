package com.megganbz.data

import com.megganbz.data.remote.RemoteApiService
import com.megganbz.domain.model.Movie
import com.megganbz.domain.repository.IMoviesRepository

class MoviesRepository(private val remoteApiService: RemoteApiService) : IMoviesRepository {
    private val apiKey = "3c5e3dd17b0d1aedb9eec27268a77e3d"
    override suspend fun getPopularMoviesList(page: Int): List<Movie>? {
        return try {
            remoteApiService.getPopularMoviesList(apiKey, page)?.results
        } catch (e: Exception) {
            Throwable(e)
            emptyList()
        }
    }
}