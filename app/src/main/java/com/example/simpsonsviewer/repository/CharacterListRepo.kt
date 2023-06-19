package com.example.simpsonsviewer.repository

import com.example.simpsonsviewer.api.ItemListAPI
import com.example.simpsonsviewer.data.CharacterInfo
import javax.inject.Inject

class CharacterListRepo @Inject constructor(private val characterListAPI: ItemListAPI) {

    suspend fun getCharacterList(): List<CharacterInfo> {
        return characterListAPI.getItemList().characterInfoList
    }
}