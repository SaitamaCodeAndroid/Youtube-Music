package com.learnbyheart.core.model

import kotlin.String

data class MusicDisplayData(
    val id: String,
    val name: String,
    val image: String,
    val owner: String = "",
    val duration: String = "",
    val totalTrack: Int = 0,
    val totalSubscriber: Int = 0,
    val albumType: AlbumType = AlbumType.ALBUM
)
