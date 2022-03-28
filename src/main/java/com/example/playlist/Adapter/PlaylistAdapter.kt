package com.example.playlist.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playlist.R
import com.example.playlist.model.PlaylistResponse
import com.example.playlist.model.Track
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.playlist_item.view.*


class PlaylistAdapter(private val context: Context, private val playList: PlaylistResponse):
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var trackImage : ImageView
        var trackName : TextView
        var artistName : TextView

        init{
            trackImage = itemView.image_track
            trackName = itemView.name_track
            artistName = itemView.name_artist
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = playList.tracks.get(position)
        Picasso.get().load(track.images.poster.url).into(holder.trackImage)
        holder.trackName.text = track.title
        holder.artistName.text = track.artists[0].name

    }

    override fun getItemCount(): Int {
        return playList.tracks.size
    }
}