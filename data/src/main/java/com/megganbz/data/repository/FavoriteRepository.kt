package com.megganbz.data.repository

import com.megganbz.data.toCharactersEntity
import com.megganbz.data.toCharactersModel
import com.megganbz.data.utils.App
import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.repository.IFavoriteRepository

class FavoriteRepository : IFavoriteRepository {
    private val charactersDao by lazy { App.localDatabase?.charactersDao() }

    override suspend fun addCharacters(characters: Characters) {
        charactersDao?.insert(characters.toCharactersEntity())
    }

    override suspend fun getFavoriteCharacters(): List<Characters>? {
        return try {
            charactersDao?.getCharactersList()?.map { it.toCharactersModel() }
        } catch (e: Throwable) {
            Throwable(e)
            emptyList()
        }
    }

    override suspend fun removeAllCharacters() {
        charactersDao?.removeAllCharacters()
    }

    override suspend fun removeCharactersById(id: Int) {
        charactersDao?.removeCharactersById(id)
    }

}