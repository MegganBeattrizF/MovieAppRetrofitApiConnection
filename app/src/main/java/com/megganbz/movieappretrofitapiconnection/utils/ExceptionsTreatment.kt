package com.megganbz.movieappretrofitapiconnection.utils

import android.accounts.NetworkErrorException
import android.content.Context
import com.megganbz.movieappretrofitapiconnection.R

class NetworkException() : NetworkErrorException() {
    fun getLocalizedMessage(context: Context): String {
        return context.getString(R.string.internetError)
    }
}

class GeneralException() : Exception() {
    fun getLocalizedMessage(context: Context): String {
        return context.getString(R.string.genericError)
    }
}
