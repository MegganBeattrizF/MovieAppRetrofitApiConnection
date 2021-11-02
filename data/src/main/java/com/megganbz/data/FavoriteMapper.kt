package com.megganbz.data

import com.megganbz.data.local.CharactersEntity
import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.model.characters.Image

fun CharactersEntity.toCharactersModel(): Characters {
    return Characters(
        id = idCharacters,
        name = name,
        thumbnail = Image(
            pathImage,
            extension
        )
    )
}

fun Characters.toCharactersEntity(): CharactersEntity {
    return CharactersEntity(
        idCharacters = id,
        name = name,
        pathImage = thumbnail?.path,
        extension = thumbnail?.extension
    )
}