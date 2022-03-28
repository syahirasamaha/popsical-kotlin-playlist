package com.example.playlist.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("access_token")
    var accessToken: String

)