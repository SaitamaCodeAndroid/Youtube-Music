package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName
import com.learnbyheart.core.model.Album

data class AlbumMetadata(
    @SerializedName("items")
    val albums: List<Album>
)
