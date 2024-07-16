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
    ALL("all", "all"),
    TRACK("track", "songs"),
    ALBUM("album", "albums"),
    ARTIST("artist", "artists"),
    PLAYLIST("playlist", "community playlists")
}

fun getAllTypeNameAsString() = SearchType.entries
    .drop(1).joinToString(",") {
        it.typeName
    }

fun getAllTypeNameAsList() = SearchType.entries.toList().map { type ->
    type.displayName.replaceFirstChar {
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
    val tracks: SearchTrackMetadata,
    val albums: SearchAlbumMetadata,
    val artists: SearchArtistMetadata,
    val playlists: SearchPlaylistMetadata
) {

    fun toSearchDisplayData() = SearchDisplayData(
        tracks = tracks.items,
        albums = albums.items,
        artists = artists.items,
        playlists = playlists.items
    )
}

data class SearchTrackMetadata(
    val items: List<Track>
)

data class SearchAlbumMetadata(
    val items: List<Album>
)

data class SearchArtistMetadata(
    val items: List<Artist>
)

data class SearchPlaylistMetadata(
    val items: List<Playlist>
)

data class SearchDisplayData(
    val tracks: List<Track>,
    val albums: List<Album>,
    val artists: List<Artist>,
    val playlists: List<Playlist>
)
