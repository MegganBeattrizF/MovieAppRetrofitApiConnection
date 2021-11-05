package com.megganbz.data.repository

import android.content.SharedPreferences
import com.megganbz.data.toCharactersEntity
import com.megganbz.data.toCharactersModel
import com.megganbz.data.utils.App
import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.repository.IFavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FavoriteRepository(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val sharedPreferences: SharedPreferences = App.sharedPreference
) : IFavoriteRepository {
    private val charactersDao by lazy { App.localDatabase?.charactersDao() }

    override suspend fun addCharacters(characters: Characters): Unit =
        withContext(context = ioDispatcher) {
            charactersDao?.insert(characters.toCharactersEntity())
        }

    override suspend fun getFavoriteCharacters(): List<Characters>? =
        withContext(context = ioDispatcher) {
            try {
                charactersDao?.getCharactersList()?.map { it.toCharactersModel() }
            } catch (e: Throwable) {
                Throwable(e)
                emptyList()
            }
        }

    override suspend fun removeAllCharacters(): Unit = withContext(context = ioDispatcher) {
        charactersDao?.removeAllCharacters()
    }

    override suspend fun removeCharactersById(id: Int): Unit = withContext(context = ioDispatcher) {
        charactersDao?.removeCharactersById(id)
    }

    override suspend fun removeFavoritePreferencesById(id: Int): Unit =
        withContext(context = ioDispatcher) {
            val idFavoriteCharacter = sharedPreferences.getString(PREFERENCES_KEY, null)
            val idCharactersList = idFavoriteCharacter?.let {
                Json.decodeFromString<ArrayList<Int>>(
                    it
                )
            }
            val edit = sharedPreferences.edit()
            idCharactersList?.remove(id)
            val jsonIdCharactersList = Json.encodeToString(idCharactersList)
            edit?.putString(PREFERENCES_KEY, jsonIdCharactersList)
            edit?.apply()
        }

    override suspend fun saveFavoritePreferences(
        characters: Characters,
        idCharacterList: MutableList<Int>
    ): Unit = withContext(context = ioDispatcher) {
        val edit = sharedPreferences.edit()
        val jsonFavoriteItemList = Json.encodeToString(idCharacterList)
        edit?.putString(PREFERENCES_KEY, jsonFavoriteItemList)
        edit?.apply()
    }

    override suspend fun removeAllFavoritesPreferences(): Unit =
        withContext(context = ioDispatcher) {
            sharedPreferences.edit().clear().apply()
        }

    companion object {
        const val PREFERENCES_KEY = "id"
    }
}