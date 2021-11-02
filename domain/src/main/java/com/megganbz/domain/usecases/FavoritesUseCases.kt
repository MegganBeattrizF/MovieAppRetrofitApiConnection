package com.megganbz.domain.usecases

import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.repository.IFavoriteRepository

class FavoritesUseCases(private val repository: IFavoriteRepository) {
    suspend fun addCharacters(characters: Characters){
        repository.addCharacters(characters)
    }

    suspend fun getCharacters(): List<Characters>?{
        return repository.getFavoriteCharacters()
    }

    suspend fun removeAllCharacters(){
        repository.removeAllCharacters()
    }

    suspend fun removeCharactersById(id: Int){
        repository.removeCharactersById(id)
    }
}