package com.example.playlist.model


import com.google.gson.annotations.SerializedName

data class PlaylistResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("tracks")
    val tracks: List<Track>,
)