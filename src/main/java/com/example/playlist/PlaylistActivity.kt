package com.example.playlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlist.Adapter.PlaylistAdapter
import com.example.playlist.model.PlaylistResponse
import com.example.playlist.network.ApiClient
import com.example.playlist.network.PrefService
import kotlinx.android.synthetic.main.activity_playlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlaylistActivity : AppCompatActivity() {
    private lateinit var prefService: PrefService
    private lateinit var apiClient: ApiClient
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter : PlaylistAdapter
    private lateinit var playlistTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        apiClient = ApiClient()
        prefService = PrefService(this)

        recyclerPlaylist.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerPlaylist.layoutManager = layoutManager

        playlistTitle = findViewById(R.id.textView)

        fetchPlaylist()

    }

    private fun fetchPlaylist() {

        apiClient.getApiService(this).fetchPlaylist(token = "Bearer ${prefService.fetchAuthToken()}")
            .enqueue(object : Callback<PlaylistResponse> {
                override fun onFailure(call: Call<PlaylistResponse>, t: Throwable) {

                }

                override fun onResponse(call: Call<PlaylistResponse>, response: Response<PlaylistResponse>) {
                    val responseTitle = response.body()
                    val myPlaylistTitle = StringBuilder()
                    if (responseTitle != null) {
                        myPlaylistTitle.append(responseTitle.name)
                    }

                    playlistTitle.text = myPlaylistTitle

                    adapter = PlaylistAdapter(baseContext, response.body() as PlaylistResponse)
                    recyclerPlaylist.adapter = adapter
                }
            })
    }
}