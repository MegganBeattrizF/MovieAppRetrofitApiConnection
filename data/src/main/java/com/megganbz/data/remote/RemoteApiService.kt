package com.megganbz.data.remote

import com.megganbz.data.dto.CharacterDataWrapper
import com.megganbz.data.dto.MoviesCreditsResponse
import com.megganbz.data.dto.MoviesListPageResult
import com.megganbz.domain.movies.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApiService {

    @GET("/v1/public/characters")
    suspend fun getCharactersList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String = getTimeStamp(),
        @Query("hash") hash: String = getHash()
    ): CharacterDataWrapper?

    @GET("/v1/public/characters/{charactersId}")
    suspend fun getCharactersDetails(
        @Path("charactersId") characterId: Int,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String = getTimeStamp(),
        @Query("hash") hash: String = getHash()
    ): CharacterDataWrapper?

    @GET("movie/popular")
    suspend fun getPopularMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MoviesListPageResult?

    @GET("movie/{movie_id}")
    suspend fun getMovieDescription(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): MovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): MoviesCreditsResponse
}