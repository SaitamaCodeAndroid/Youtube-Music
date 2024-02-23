package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName

data class PlayListResponse(
    @SerializedName("playlists")
    val playlistMetadata: PlaylistMetadata,
)
