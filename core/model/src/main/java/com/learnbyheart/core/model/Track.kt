package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName

data class Track(
    val id: String,
    val name: String,
    @SerializedName("preview_url")
    val musicUrl: String?,
    val album: Album,
    val artists: List<Artist>
) {

    fun toTrackDisplayData() = TrackDisplayData(
        id = id,
        name = name,
        musicUrl = musicUrl ?: "",
        image = album.images[0].url,
        artists = artists.joinToString(" & ") { artist ->
            artist.name
        }
    )
}

data class TrackDisplayData(
    val id: String,
    val name: String,
    val musicUrl: String,
    val image: String,
    val artists: String
)
