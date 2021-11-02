package com.megganbz.movieappretrofitapiconnection.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megganbz.data.repository.MoviesRepository
import com.megganbz.data.remote.buildApiService
import com.megganbz.domain.movies.Movie
import com.megganbz.domain.usecases.MoviesUseCases
import com.megganbz.movieappretrofitapiconnection.utils.*
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val baseUrl = "https://api.themoviedb.org/3/"
    private val apiService by lazy { buildApiService(baseUrl) }
    private var moviesUseCases = MoviesUseCases(MoviesRepository(apiService))

    private var _moviesList = MutableLiveData<Result<List<Movie>?>>()
    val moviesList: LiveData<Result<List<Movie>?>>
        get() = _moviesList

    fun getPopularMoviesList(page: Int) {
        viewModelScope.launch {
            try {
                _moviesList.postValue(Success(moviesUseCases.getPopularMoviesList(page)))
            } catch (e: NetworkException) {
                _moviesList.postValue(Failure(e))
            } catch (e: GeneralException) {
                _moviesList.postValue(Failure(e))
            }
        }
    }
}