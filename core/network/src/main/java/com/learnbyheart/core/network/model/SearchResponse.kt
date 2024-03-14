package com.learnbyheart.core.network.model

import com.learnbyheart.core.model.Album
import com.learnbyheart.core.model.Artist
import com.learnbyheart.core.model.Playlist
import com.learnbyheart.core.model.Track
import java.util.Locale

enum class SearchType(
    val typeName: String,
    val displayName: String
) {
    TRACK("track", "tracks"),
    ALBUM("album", "albums"),
    ARTIST("artist", "artists"),
    PLAYLIST("playlist", "playlists")
}

fun getAllTypeNameAsString() = SearchType.entries.joinToString(",") { searchType ->
    searchType.typeName.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(
                Locale.getDefault()
            )
        } else {
            it.toString()
        }
    }
}

fun getAllTypeNameAsList() = SearchType.entries.toList().map { type ->
    type.typeName.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(
                Locale.ROOT
            )
        } else {
            it.toString()
        }
    }
}

data class SearchResponse(
    val tracks: List<Track>,
    val albums: List<Album>,
    val artists: List<Artist>,
    val playlists: List<Playlist>,
)
