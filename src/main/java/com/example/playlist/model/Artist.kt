package com.example.playlist.model


import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name")
    val name: String,
)