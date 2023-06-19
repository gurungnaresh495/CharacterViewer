package com.example.simpsonsviewer.api

import com.example.simpsonsviewer.BuildConfig
import com.example.simpsonsviewer.data.APIResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ItemListAPI {

    @GET(BuildConfig.URL)
    suspend fun getItemList(): APIResponse
}