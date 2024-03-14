package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName
import kotlin.String

enum class AlbumType(val typeName: String) {
    ALBUM("album"),
    SINGLE("single")
}

data class Album(
    val id: String,
    @SerializedName("artists")
    val artists: List<Artist>,
    val images: List<Image>,
    val name: String,
    @SerializedName("total_tracks")
    val totalTracks: Int,
    val type: String,
) {

    fun toAlbumDisplayData() = AlbumDisplayData(
        id = id,
        name = name,
        artists = artists.joinToString(" & ") { artist ->
            artist.name
        },
        image = images[0].url,
        type = if (type == "single") {
            AlbumType.SINGLE
        } else {
            AlbumType.ALBUM
        }
    )
}

data class AlbumDisplayData(
    val id: String,
    val name: String,
    val artists: String,
    val image: String,
    val type: AlbumType,
)
