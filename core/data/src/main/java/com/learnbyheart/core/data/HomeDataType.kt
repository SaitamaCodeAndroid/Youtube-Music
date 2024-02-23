package com.learnbyheart.core.data

enum class HomeDataType(val header: String) {
    RECOMMENDATION_TRACK(header = "Quick picks"),
    RECOMMENDATION_ALBUM(header = "Recommended albums"),
    POPULAR_TRACK(header = "Trending songs"),
    NEW_RELEASE_ALBUM(header = "New release albums"),
    FEATURED_PLAYLIST(header = "Featured playlists")
}
