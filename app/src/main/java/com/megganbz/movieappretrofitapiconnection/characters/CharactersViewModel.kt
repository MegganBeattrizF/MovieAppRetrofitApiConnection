package com.megganbz.movieappretrofitapiconnection.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megganbz.data.remote.buildApiService
import com.megganbz.data.repository.CharactersRepository
import com.megganbz.data.repository.FavoriteRepository
import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.usecases.CharactersUseCases
import com.megganbz.domain.usecases.FavoritesUseCases
import com.megganbz.movieappretrofitapiconnection.utils.*
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private val baseUrl = "https://gateway.marvel.com"
    private val apiService by lazy { buildApiService(baseUrl) }
    private val charactersUseCases = CharactersUseCases(CharactersRepository(apiService))
    private val favoritesUseCases = FavoritesUseCases(FavoriteRepository())

    private var _charactersList = MutableLiveData<Result<List<Characters>?>>()
    val charactersList: LiveData<Result<List<Characters>?>>
        get() = _charactersList

    private var _charactersDetails = MutableLiveData<Result<List<Characters>?>>()
    val charactersDetails: LiveData<Result<List<Characters>?>>
        get() = _charactersDetails

    fun getCharactersList(limit: Int, offset: Int) {
        viewModelScope.launch {
            try {
                _charactersList.postValue(
                    Success(
                        charactersUseCases.getCharactersList(
                            limit,
                            offset
                        )
                    )
                )
            } catch (e: NetworkException) {
                _charactersList.postValue(Failure(e))
            } catch (e: GeneralException) {
                _charactersList.postValue(Failure(e))
            }
        }
    }

    fun getCharactersDetails(characterId: Int) {
        viewModelScope.launch {
            try {
                _charactersDetails.postValue(
                    Success(
                        charactersUseCases.getCharactersDetails(
                            characterId
                        )
                    )
                )
            } catch (e: NetworkException) {
                _charactersDetails.postValue(Failure(e))
            } catch (e: GeneralException) {
                _charactersDetails.postValue(Failure(e))
            }
        }
    }

    fun saveFavoriteCharacter(characters: Characters, idCharacterList: MutableList<Int>) {
        viewModelScope.launch {
            favoritesUseCases.addCharacters(characters)
            favoritesUseCases.saveFavoritePreferences(characters, idCharacterList)
        }
    }
}