package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName

data class Track(
    val id: String,
    val name: String,
    @SerializedName("preview_url")
    val previewUrl: String,
    val album: Album,
    val artists: List<Artist>,
)
