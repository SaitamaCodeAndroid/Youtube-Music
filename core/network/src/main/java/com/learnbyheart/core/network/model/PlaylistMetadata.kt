package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName
import com.learnbyheart.core.model.Playlist

data class PlaylistMetadata(
    @SerializedName("items")
    val playLists: List<Playlist>,
    val total: Int,
)
