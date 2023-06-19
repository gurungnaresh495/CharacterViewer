package com.example.simpsonsviewer.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterInfo(
    @SerializedName("Icon")
    val imageInfo: ImageInfo,
    @SerializedName("Text")
    val characterInfo: String
) : Parcelable