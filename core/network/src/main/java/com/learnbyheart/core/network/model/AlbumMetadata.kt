package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName

data class AlbumMetadata(
    @SerializedName("items")
    val albums: List<Album>
)
