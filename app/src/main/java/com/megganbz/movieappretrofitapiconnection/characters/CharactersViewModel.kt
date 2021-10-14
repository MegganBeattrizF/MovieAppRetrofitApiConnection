package com.megganbz.movieappretrofitapiconnection.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megganbz.data.CharactersRepository
import com.megganbz.data.remote.buildApiService
import com.megganbz.domain.model.Characters
import com.megganbz.domain.usecases.CharactersUseCases
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private val apiService by lazy { buildApiService() }
    private var charactersUseCases = CharactersUseCases(CharactersRepository(apiService))

    private var _charactersList = MutableLiveData<List<Characters>>()
    val charactersList: LiveData<List<Characters>>
        get() = _charactersList

    fun getCharactersList() {
        viewModelScope.launch {
            _charactersList.value = charactersUseCases.getCharactersList()
        }
    }
}