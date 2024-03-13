package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName

data class Playlist(
    val id: String,
    val description: String,
    val images: List<Image>,
    val name: String,
    @SerializedName("primary_color")
    val primaryColor: String?,
    val owner: Owner,
) {

    fun toPlaylistDisplayData() = PlaylistDisplayData(
        id = id,
        name = name,
        description = description,
        image = images[0].url,
        primaryColor = primaryColor ?: "#000000",
        owner = owner
    )
}

data class PlaylistDisplayData(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val primaryColor: String,
    val owner: Owner,
)
