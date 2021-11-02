package com.megganbz.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CHARACTERS")
data class CharactersEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_characters") val idCharacters: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "path_image") val pathImage: String?,
    @ColumnInfo(name = "extension_image") val extension: String?
)
