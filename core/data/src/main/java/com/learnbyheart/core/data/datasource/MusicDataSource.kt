package com.learnbyheart.core.data.datasource

import com.learnbyheart.core.network.service.MusicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val DEFAULT_COUNTRY_CODE = "US"
private const val DEFAULT_LOCALE = "en-US"

class MusicDataSource @Inject constructor(
    private val service: MusicService
) {

    suspend fun getRecommendationTracks(
        token: String,
        genres: String,
    ) = withContext(Dispatchers.IO) {
        service.getRecommendationTracks(
            authorization = token,
            countryCode = DEFAULT_COUNTRY_CODE,
            genres = genres,
        )
    }

    suspend fun getPopularTracks(
        token: String,
        genres: String,
    ) = withContext(Dispatchers.IO) {
        service.getRecommendationTracks(
            authorization = token,
            countryCode = DEFAULT_COUNTRY_CODE,
            genres = genres,
        )
    }

    suspend fun getCategories(
        token: String,
    ) = withContext(Dispatchers.IO) {
        service.getCategories(
            authorization = token,
            locale = DEFAULT_LOCALE,
        )
    }

    suspend fun getNewReleaseAlbums(
        token: String,
    ) = withContext(Dispatchers.IO) {
        service.getNewReleaseAlbums(
            authorization = token,
            locale = DEFAULT_LOCALE,
        )
    }

    suspend fun getFeaturedPlaylist(
        token: String,
    ) = withContext(Dispatchers.IO) {
        service.getFeaturedPlaylist(
            authorization = token,
            locale = DEFAULT_LOCALE,
        )
    }
}
