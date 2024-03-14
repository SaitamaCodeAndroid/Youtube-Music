package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName
import kotlin.String

data class Artist(
    val id: String,
    val name: String,
    val images: List<Image>,
    @SerializedName("followers")
    val subscriber: AdditionalInfo
) {

    fun toArtistDisplayData() = ArtistDisplayData(
        id = id,
        name = name,
        image = images[0].url,
        totalSubscriber = subscriber.total
    )
}

data class ArtistDisplayData(
    val id: String,
    val name: String,
    val image: String,
    val totalSubscriber: Int,
)
