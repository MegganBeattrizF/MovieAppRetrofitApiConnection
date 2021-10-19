package com.megganbz.movieappretrofitapiconnection.utils

import android.content.Context
import com.megganbz.movieappretrofitapiconnection.R

class NetworkException() : Exception() {
    fun getLocalizedMessage(context: Context): String {
        return context.getString(R.string.internetError)
    }
}

class CredentialException() : Exception() {
    fun getLocalizedMessage(context: Context): String {
        return context.getString(R.string.genericError)
    }
}

class AuthorizationException() : Exception() {
    fun getLocalizedMessage(context: Context): String {
        return context.getString(R.string.genericError)
    }
}
