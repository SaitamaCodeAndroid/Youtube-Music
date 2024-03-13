package com.learnbyheart.core.network.model

import com.learnbyheart.core.model.Album
import com.learnbyheart.core.model.Artist
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.model.Playlist
import com.learnbyheart.core.model.Track

enum class SearchType(val typeName: String) {
    TRACK("tracks"),
    ALBUM("albums"),
    ARTIST("artists"),
    PLAYLIST("playlist community")
}

data class SearchResponse(
    val tracks: List<Track>,
    val albums: List<Album>,
    val artists: List<Artist>,
    val playlists: List<Playlist>,
)
