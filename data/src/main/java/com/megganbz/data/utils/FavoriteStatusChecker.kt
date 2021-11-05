package com.megganbz.data.utils

import com.megganbz.data.repository.FavoriteRepository.Companion.PREFERENCES_KEY
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FavoriteStatusChecker {
    fun isFavoriteItem(id: Int?): Boolean {
        val idFavoriteCharacter =
            App.sharedPreference.getString(PREFERENCES_KEY, null)
        val idCharactersList = idFavoriteCharacter?.let {
            Json.decodeFromString<ArrayList<Int>>(it)
        }
        return if (idCharactersList.isNullOrEmpty().not()) {
            idCharactersList?.contains(id)!!
        } else {
            false
        }
    }
}