package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName
import java.util.Calendar
import java.util.Date
import kotlin.String

data class Track(
    val id: String,
    val name: String,
    @SerializedName("preview_url")
    val musicUrl: String?,
    @SerializedName("duration_ms")
    val duration: Long,
    val album: Album,
    val artists: List<Artist>
) {

    fun toTrackDisplayData(): TrackDisplayData {
        val calendar = Calendar.getInstance()
        calendar.time = Date(duration)

        return TrackDisplayData(
            id = id,
            name = name,
            musicUrl = musicUrl ?: "",
            duration = "${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}",
            image = album.images[0].url,
            artists = artists.joinToString(" & ") { artist ->
                artist.name
            }
        )
    }
}

data class TrackDisplayData(
    val id: String,
    val name: String,
    val musicUrl: String,
    val duration: String,
    val image: String,
    val artists: String
)
