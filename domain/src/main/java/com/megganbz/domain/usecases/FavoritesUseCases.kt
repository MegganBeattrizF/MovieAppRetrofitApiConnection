package com.megganbz.domain.usecases

import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.repository.IFavoriteRepository

class FavoritesUseCases(private val repository: IFavoriteRepository) {

    suspend fun addCharacters(characters: Characters) {
        repository.addCharacters(characters)
    }

    suspend fun getCharacters(): List<Characters>? {
        return repository.getFavoriteCharacters()
    }

    suspend fun removeAllCharacters() {
        repository.removeAllCharacters()
    }

    suspend fun removeCharactersById(id: Int) {
        repository.removeCharactersById(id)
    }

    suspend fun removeFavoritePreferencesById(id: Int) {
        repository.removeFavoritePreferencesById(id)
    }

    suspend fun removeAllFavoritesPreferences() {
        repository.removeAllFavoritesPreferences()
    }

    suspend fun saveFavoritePreferences(characters: Characters, idCharacterList: MutableList<Int>) {
        repository.saveFavoritePreferences(characters, idCharacterList)
    }
}