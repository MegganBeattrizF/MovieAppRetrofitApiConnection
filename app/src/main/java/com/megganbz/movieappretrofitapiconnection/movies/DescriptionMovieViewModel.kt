package com.megganbz.movieappretrofitapiconnection.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.megganbz.data.repository.MoviesRepository
import com.megganbz.data.remote.buildApiService
import com.megganbz.domain.model.movies.Cast
import com.megganbz.domain.model.movies.Crew
import com.megganbz.domain.movies.MovieDetails
import com.megganbz.domain.usecases.MoviesUseCases
import com.megganbz.movieappretrofitapiconnection.utils.*
import kotlinx.coroutines.launch

class DescriptionMovieViewModel : ViewModel() {
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val apiService by lazy { buildApiService(baseUrl) }
    private var moviesUseCases = MoviesUseCases(MoviesRepository(apiService))
    private var _moviesDescription = MutableLiveData<Result<MovieDetails?>>()

    val moviesDescription: LiveData<Result<MovieDetails?>>
        get() = _moviesDescription
    private var _movieCast = MutableLiveData<Result<List<Cast>?>>()
    val cast: LiveData<Result<List<Cast>?>>
        get() = _movieCast
    private var _movieCrew = MutableLiveData<Result<List<Crew>?>>()
    val crew: LiveData<Result<List<Crew>?>>
        get() = _movieCrew

    fun getMovieDescription(movieId: Int?) {
        viewModelScope.launch {
            try {
                _moviesDescription.postValue(Success(movieId?.let {
                    moviesUseCases.getMovieDescription(it)
                }))
            } catch (e: NetworkException) {
                _moviesDescription.postValue(Failure(e))
            } catch (e: GeneralException) {
                _moviesDescription.postValue(Failure(e))
            }
        }
    }

    fun getMovieCast(movieId: Int?) {
        viewModelScope.launch {
            try {
                _movieCast.postValue(Success(movieId?.let {
                    moviesUseCases.getMovieCast(it)
                }))
            } catch (e: NetworkException) {
                _movieCast.postValue(Failure(e))
            } catch (e: GeneralException) {
                _movieCast.postValue(Failure(e))
            }
        }
    }

    fun getMovieCrew(movieId: Int?) {
        viewModelScope.launch {
            try {
                _movieCrew.postValue(Success(movieId?.let {
                    moviesUseCases.getMovieDirector(it)
                }))
            } catch (e: NetworkException) {
                _movieCrew.postValue(Failure(e))
            } catch (e: GeneralException) {
                _movieCrew.postValue(Failure(e))
            }
        }
    }
}