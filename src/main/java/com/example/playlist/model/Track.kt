package com.example.playlist.model

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("title")
    val title: String,
    @SerializedName("artists")
    val artists: List<Artist>,
    @SerializedName("images")
    val images: Images,
)