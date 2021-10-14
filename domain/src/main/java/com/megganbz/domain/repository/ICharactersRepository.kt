package com.megganbz.domain.repository

import com.megganbz.domain.model.Characters

interface ICharactersRepository {
    suspend fun getCharactersList(): List<Characters>?
}