package com.example.playlist.model


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("url")
    val url: String
)