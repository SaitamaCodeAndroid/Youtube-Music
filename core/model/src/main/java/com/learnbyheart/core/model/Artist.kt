package com.learnbyheart.core.model

data class Artist(
    val id: String,
    val name: String,
    val images: List<Image>,
) {

    fun toArtistDisplayData() = ArtistDisplayData(
        id = id,
        name = name,
        image = images[0].url,
    )
}

data class ArtistDisplayData(
    val id: String,
    val name: String,
    val image: String,
)
