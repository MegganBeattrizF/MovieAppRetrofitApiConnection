package com.megganbz.movieappretrofitapiconnection.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megganbz.data.repository.FavoriteRepository
import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.usecases.FavoritesUseCases
import com.megganbz.movieappretrofitapiconnection.utils.Result
import com.megganbz.movieappretrofitapiconnection.utils.Success
import kotlinx.coroutines.launch

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
            favoritesUseCases.removeAllFavoritesPreferences()
        }
    }

    fun removeCharactersById(id: Int) {
        viewModelScope.launch {
            favoritesUseCases.removeCharactersById(id)
            favoritesUseCases.removeFavoritePreferencesById(id)
        }
    }
}