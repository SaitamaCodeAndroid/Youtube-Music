package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName

data class Track(
    val id: String,
    val name: String,
    @SerializedName("preview_url")
    val previewUrl: String,
    val album: com.learnbyheart.core.model.Album,
    val artists: List<com.learnbyheart.core.model.Artist>,
)
