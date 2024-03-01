package com.learnbyheart.core.data

enum class HomeDataType(val header: String) {
    RECOMMENDATION_TRACK(header = "Quick picks"),
    RECOMMENDATION_ALBUM(header = "Recommendation albums"),
    POPULAR_TRACK(header = "Trending songs"),
    NEW_RELEASE_ALBUM(header = "New release albums"),
    FEATURED_PLAYLIST(header = "Recommendation playlists"),
    NEW_RELEASE_PLAYLIST(header = "New releases")
}
