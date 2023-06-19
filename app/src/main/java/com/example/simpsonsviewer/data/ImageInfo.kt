package com.example.simpsonsviewer.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageInfo(
    @SerializedName("URL")
    val url: String,
): Parcelable