package com.megganbz.movieappretrofitapiconnection.utils

import com.megganbz.data.utils.App
import com.megganbz.movieappretrofitapiconnection.characters.CharactersAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FavoriteStatusChecker {
    fun isFavoriteItem(id: Int?): Boolean {
        val idFavoriteCharacter =
            App.sharedPreference.getString(CharactersAdapter.PREFERENCES_KEY, null)
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