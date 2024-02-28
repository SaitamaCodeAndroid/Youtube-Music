package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName

data class Album(
    val id: String,
    @SerializedName("artists")
    val artists: List<Artist>,
    val images: List<Image>,
    val name: String,
    @SerializedName("total_tracks")
    val totalTracks: Int,
    val type: String,
)
