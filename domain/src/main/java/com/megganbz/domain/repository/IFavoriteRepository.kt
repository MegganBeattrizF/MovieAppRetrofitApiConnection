package com.megganbz.domain.repository

import com.megganbz.domain.model.characters.Characters

interface IFavoriteRepository {
    suspend fun addCharacters(characters: Characters)
    suspend fun getFavoriteCharacters(): List<Characters>?
    suspend fun removeAllCharacters()
    suspend fun removeCharactersById(id: Int)
    suspend fun removeFavoritePreferencesById(id: Int)
    suspend fun saveFavoritePreferences(characters: Characters, idCharacterList: MutableList<Int>)
    suspend fun removeAllFavoritesPreferences()
}