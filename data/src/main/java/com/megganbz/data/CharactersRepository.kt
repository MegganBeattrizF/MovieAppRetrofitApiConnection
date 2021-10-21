package com.megganbz.data

import com.megganbz.data.remote.RemoteApiService
import com.megganbz.domain.model.Characters
import com.megganbz.domain.repository.ICharactersRepository

class CharactersRepository(private val remoteApiService: RemoteApiService) : ICharactersRepository {
    private val apiKey = "0c0f5aa7e78457217f6f1d1f0361bdfb"
    override suspend fun getCharactersList(limit: Int, offset: Int): List<Characters>? {
        return try {
            remoteApiService.getCharactersList(limit, offset, apiKey)?.data?.results
        } catch (e: Throwable) {
            Throwable(e)
            emptyList()
        }
    }
}