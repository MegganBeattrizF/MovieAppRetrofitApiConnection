package com.megganbz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharactersEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}