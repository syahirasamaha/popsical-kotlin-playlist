package com.example.playlist.network

import android.content.Context
import com.example.playlist.model.LoginRequest
import com.example.playlist.model.LoginResponse
import com.example.playlist.model.PlaylistResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET(Constants.PLAYLIST_URL)
    fun fetchPlaylist(@Header("Authorization") token: String): Call<PlaylistResponse>

}

class ApiClient {
    private lateinit var apiService: ApiService

    fun getApiService(context: Context): ApiService {
        if(!::apiService.isInitialized)  {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
}