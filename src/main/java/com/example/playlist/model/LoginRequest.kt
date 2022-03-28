package com.example.playlist.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("device_id")
    var deviceId: String,

    @SerializedName("client_id")
    var clientId: String,

    @SerializedName("scope")
    var scope: String

)