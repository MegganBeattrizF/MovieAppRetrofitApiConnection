package com.megganbz.movieappretrofitapiconnection.utils

sealed class Result<out T>

data class Success<out T>(val data: T) : Result<T>()

data class Failure<out T>(val error: Throwable?) : Result<T>()