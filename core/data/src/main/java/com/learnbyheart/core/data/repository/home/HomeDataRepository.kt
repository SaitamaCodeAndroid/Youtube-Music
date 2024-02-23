package com.learnbyheart.core.data.repository.home

import com.learnbyheart.core.network.model.AlbumResponse
import com.learnbyheart.core.network.model.CategoryResponse
import com.learnbyheart.core.network.model.PlayListResponse
import com.learnbyheart.core.network.model.RecommendationTrackResponse
import kotlinx.coroutines.flow.Flow

interface HomeDataRepository {

    suspend fun getRecommendationTracks(
        token: String,
    ): Flow<RecommendationTrackResponse>

    suspend fun getPopularTracks(
        token: String,
    ): Flow<RecommendationTrackResponse>

    suspend fun getCategories(
        token: String,
    ): Flow<CategoryResponse>

    suspend fun getNewReleaseAlbums(
        token: String,
    ): Flow<AlbumResponse>

    suspend fun getFeaturedPlaylist(
        token: String,
    ): Flow<PlayListResponse>
}
