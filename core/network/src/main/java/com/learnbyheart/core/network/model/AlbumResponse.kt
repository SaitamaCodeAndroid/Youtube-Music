package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    @SerializedName("albums")
    val albumMetadata: AlbumMetadata
)
