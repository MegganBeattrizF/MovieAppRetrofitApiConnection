package com.megganbz.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

fun buildClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

fun buildRetrofit(baseUrl: String): Retrofit {
    val contentType = "application/json".toMediaType()
    //val baseUrl = "https://gateway.marvel.com"

    return Retrofit.Builder()
        .client(buildClient())
        .baseUrl(baseUrl)
        .addConverterFactory(Json {
            isLenient = true
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType))
        .build()
}

fun buildApiService(baseUrl: String): RemoteApiService =
    buildRetrofit(baseUrl).create(RemoteApiService::class.java)