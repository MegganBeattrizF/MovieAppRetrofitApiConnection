package com.megganbz.data.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.megganbz.data.local.AppDatabase

class App : Application() {
    companion object {
        var localDatabase: AppDatabase? = null
        lateinit var sharedPreference: SharedPreferences
    }

    private fun init(context: Context) {
        localDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "MovieAppDatabase"
        ).build()

        sharedPreference = getSharedPreferences(
            "FAVORITES_PREFERENCE",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}