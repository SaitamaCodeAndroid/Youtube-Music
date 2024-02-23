package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName

data class Track(
    val id: String,
    val name: String,
    @SerializedName("preview_url")
    val musicUrl: String,
    val album: Album,
    val artists: List<Artist>,
)
