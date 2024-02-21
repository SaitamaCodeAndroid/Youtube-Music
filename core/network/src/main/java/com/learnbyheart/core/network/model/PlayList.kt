package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName

data class PlayList(
    val id: String,
    val description: String,
    val images: List<Image>,
    val name: String,
    @SerializedName("primary_color")
    val primaryColor: String,
)
