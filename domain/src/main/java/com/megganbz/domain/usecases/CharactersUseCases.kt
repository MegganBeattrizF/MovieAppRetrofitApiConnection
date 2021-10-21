package com.megganbz.domain.usecases

import com.megganbz.domain.model.Characters
import com.megganbz.domain.repository.ICharactersRepository

class CharactersUseCases(private val repository: ICharactersRepository) {

    suspend fun getCharactersList(limit: Int, offset: Int): List<Characters>? {
        return repository.getCharactersList(limit, offset)
    }
}