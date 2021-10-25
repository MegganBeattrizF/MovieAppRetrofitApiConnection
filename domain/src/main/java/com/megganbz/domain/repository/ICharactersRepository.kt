package com.megganbz.domain.repository

import com.megganbz.domain.model.characters.Characters

interface ICharactersRepository {
    suspend fun getCharactersList(limit: Int, offset: Int): List<Characters>?
    suspend fun getCharactersDetails(characterId: Int): List<Characters>?
}