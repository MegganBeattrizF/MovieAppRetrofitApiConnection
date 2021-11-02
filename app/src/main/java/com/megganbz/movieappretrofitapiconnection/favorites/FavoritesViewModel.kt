package com.megganbz.movieappretrofitapiconnection.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megganbz.data.repository.FavoriteRepository
import com.megganbz.data.utils.App
import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.usecases.FavoritesUseCases
import com.megganbz.movieappretrofitapiconnection.characters.CharactersAdapter
import com.megganbz.movieappretrofitapiconnection.utils.Result
import com.megganbz.movieappretrofitapiconnection.utils.Success
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FavoritesViewModel : ViewModel() {
    private val favoritesUseCases = FavoritesUseCases(FavoriteRepository())
    private var _favoriteCharactersList = MutableLiveData<Result<List<Characters>?>>()
    val favoriteCharactersList: LiveData<Result<List<Characters>?>>
        get() = _favoriteCharactersList

    fun getFavoriteCharactersList() {
        viewModelScope.launch {
            try {
                _favoriteCharactersList.postValue(Success(favoritesUseCases.getCharacters()))
            } catch (e: Exception) {

            }
        }
    }

    fun removeAllCharacters() {
        viewModelScope.launch {
            favoritesUseCases.removeAllCharacters()
            App.sharedPreference.edit().clear().apply()
        }
    }

    fun removeCharactersById(id: Int) {
        viewModelScope.launch {
            favoritesUseCases.removeCharactersById(id)
            val sharedPreferences = App.sharedPreference
            val idFavoriteCharacter =
                sharedPreferences.getString(CharactersAdapter.PREFERENCES_KEY, null)
            val idCharactersList = idFavoriteCharacter?.let {
                Json.decodeFromString<ArrayList<Int>>(
                    it
                )
            }
            val edit = sharedPreferences.edit()
            idCharactersList?.remove(id)
            val jsonIdCharactersList = Json.encodeToString(idCharactersList)
            edit?.putString(CharactersAdapter.PREFERENCES_KEY, jsonIdCharactersList)
            edit?.apply()
        }
    }
}