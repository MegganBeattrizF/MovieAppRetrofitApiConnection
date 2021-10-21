package com.megganbz.data.remote

import com.megganbz.data.dto.CharacterDataWrapper
import retrofit2.http.GET
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
}