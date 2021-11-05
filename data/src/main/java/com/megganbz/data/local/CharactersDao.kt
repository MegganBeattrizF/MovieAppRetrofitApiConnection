package com.megganbz.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharactersDao {
    @Insert
    suspend fun insert(vararg charactersEntity: CharactersEntity)

    @Delete
    suspend fun delete(charactersEntity: CharactersEntity)

    @Query("SELECT * FROM CHARACTERS")
    suspend fun getCharactersList(): List<CharactersEntity>

    @Query("DELETE FROM CHARACTERS")
    suspend fun removeAllCharacters()

    @Query("DELETE FROM CHARACTERS WHERE id_characters = :id")
    suspend fun removeCharactersById(id: Int)
}